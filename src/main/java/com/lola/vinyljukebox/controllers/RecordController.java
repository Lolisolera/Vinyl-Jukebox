package com.lola.vinyljukebox.controllers;

import com.lola.vinyljukebox.dto.SpotifyTrackDTO;
import com.lola.vinyljukebox.entities.Artist;
import com.lola.vinyljukebox.entities.Record;
import com.lola.vinyljukebox.repositories.ArtistRepository;
import com.lola.vinyljukebox.services.RecordService;
import com.lola.vinyljukebox.services.SpotifyIntegrationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/records")
public class RecordController {

    private final RecordService recordService;
    private final SpotifyIntegrationService spotifyService;
    private final ArtistRepository artistRepository;

    public RecordController(RecordService recordService, SpotifyIntegrationService spotifyService, ArtistRepository artistRepository) {
        this.recordService = recordService;
        this.spotifyService = spotifyService;
        this.artistRepository = artistRepository;
    }

    // GET: Search Spotify (keep this above the generic /{id} mapping)
    @GetMapping("/spotify-search")
    public List<SpotifyTrackDTO> searchSpotifyTracks(@RequestParam String query) {
        return spotifyService.searchTracks(query);
    }

    // POST: Add record from Spotify (keep this above the generic /{id} mapping)
    @PostMapping("/spotify-add")
    public Record createRecordFromSpotify(@RequestParam String trackId) {
        SpotifyTrackDTO dto = spotifyService.getTrackById(trackId);

        // Check if artist exists
        Artist artist = artistRepository.findByName(dto.getArtistName())
                .orElseGet(() -> {
                    Artist newArtist = new Artist();
                    newArtist.setName(dto.getArtistName());
                    newArtist.setBio("Imported from Spotify");
                    newArtist.setCountry("Unknown");
                    return artistRepository.save(newArtist);
                });

        Record record = new Record();
        record.setTitle(dto.getName());
        record.setSpotifyTrackId(dto.getId());
        record.setPreviewUrl(dto.getPreviewUrl());
        record.setArtist(artist);

        return recordService.createOrUpdateRecord(record);
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