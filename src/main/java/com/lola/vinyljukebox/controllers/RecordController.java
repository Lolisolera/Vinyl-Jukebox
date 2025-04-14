package com.lola.vinyljukebox.controllers;

import com.lola.vinyljukebox.dto.SpotifyTrackDTO;
import com.lola.vinyljukebox.entities.AlbumCover;
import com.lola.vinyljukebox.entities.Artist;
import com.lola.vinyljukebox.entities.Genre;
import com.lola.vinyljukebox.entities.Record;
import com.lola.vinyljukebox.repositories.ArtistRepository;
import com.lola.vinyljukebox.repositories.GenreRepository;
import com.lola.vinyljukebox.services.RecordService;
import com.lola.vinyljukebox.services.SpotifyIntegrationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/records")
public class RecordController {

    private final RecordService recordService;
    private final SpotifyIntegrationService spotifyService;
    private final ArtistRepository artistRepository;
    private final GenreRepository genreRepository;

    public RecordController(
            RecordService recordService,
            SpotifyIntegrationService spotifyService,
            ArtistRepository artistRepository,
            GenreRepository genreRepository
    ) {
        this.recordService = recordService;
        this.spotifyService = spotifyService;
        this.artistRepository = artistRepository;
        this.genreRepository = genreRepository;
    }

    // GET: Search Spotify (keep this above the generic /{id} mapping)
    @GetMapping("/spotify-search")
    public List<SpotifyTrackDTO> searchSpotifyTracks(@RequestParam String query) {
        return spotifyService.searchTracks(query);
    }

    // POST: Add record from Spotify (fully implemented)
    @PostMapping("/spotify-add")
    public Record createRecordFromSpotify(@RequestParam String trackId) {
        SpotifyTrackDTO dto = spotifyService.getTrackById(trackId);

        // 1. Find or create artist
        Artist artist = artistRepository.findByName(dto.getArtistName())
                .orElseGet(() -> {
                    Artist newArtist = new Artist();
                    newArtist.setName(dto.getArtistName());
                    newArtist.setBio("Imported from Spotify");
                    newArtist.setCountry("Unknown");
                    return artistRepository.save(newArtist);
                });

        // 2. Create and save the record
        Record record = new Record();
        record.setTitle(dto.getName());
        record.setPreviewUrl(dto.getPreviewUrl());
        record.setSpotifyTrackId(dto.getId());
        record.setArtist(artist);

        // 3. Add genres
        Set<Genre> genres = dto.getGenres().stream()
                .map(name -> genreRepository.findByName(name)
                        .orElseGet(() -> {
                            Genre g = new Genre();
                            g.setName(name);
                            return genreRepository.save(g);
                        }))
                .collect(Collectors.toSet());
        record.setGenres(genres);

        // 4. Save the record first
        Record saved = recordService.createOrUpdateRecord(record);

        // 5. Attach album cover if available
        if (dto.getCoverImageUrl() != null) {
            AlbumCover cover = new AlbumCover();
            cover.setImageUrl(dto.getCoverImageUrl());
            cover.setRecord(saved);
            saved.setAlbumCover(cover);
            return recordService.createOrUpdateRecord(saved);
        }

        return saved;
    }

    // GET: List all records
    @GetMapping
    public List<Record> getAllRecords() {
        return recordService.getAllRecords();
    }

    // GET: Get a specific record by ID — keep this LAST
    @GetMapping("/{id}")
    public Record getRecordById(@PathVariable Long id) {
        return recordService.getRecordById(id);
    }
}