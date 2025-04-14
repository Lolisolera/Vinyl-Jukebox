package com.lola.vinyljukebox.services;

import com.lola.vinyljukebox.dto.SpotifyTrackDTO;
import com.lola.vinyljukebox.entities.AlbumCover;
import com.lola.vinyljukebox.entities.Artist;
import com.lola.vinyljukebox.entities.Genre;
import com.lola.vinyljukebox.entities.Record;
import com.lola.vinyljukebox.repositories.ArtistRepository;
import com.lola.vinyljukebox.repositories.GenreRepository;
import com.lola.vinyljukebox.repositories.RecordRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class RecordService {

    private final RecordRepository recordRepository;
    private final ArtistRepository artistRepository;
    private final GenreRepository genreRepository;

    public RecordService(RecordRepository recordRepository, ArtistRepository artistRepository, GenreRepository genreRepository) {
        this.recordRepository = recordRepository;
        this.artistRepository = artistRepository;
        this.genreRepository = genreRepository;
    }

    public List<Record> getAllRecords() {
        return recordRepository.findAll();
    }

    public Record getRecordById(Long id) {
        return recordRepository.findById(id).orElse(null);
    }

    public Record createOrUpdateRecord(Record record) {
        if (record.getSpotifyTrackId() != null) {
            Optional<Record> existing = recordRepository.findBySpotifyTrackId(record.getSpotifyTrackId());
            if (existing.isPresent()) {
                Record existingRec = existing.get();
                existingRec.setTitle(record.getTitle());
                existingRec.setArtist(record.getArtist());
                existingRec.setPreviewUrl(record.getPreviewUrl());
                existingRec.setGenres(record.getGenres());

                AlbumCover incomingCover = record.getAlbumCover();
                if (incomingCover != null) {
                    AlbumCover existingCover = existingRec.getAlbumCover();
                    if (existingCover == null) {
                        // New cover for existing record
                        incomingCover.setRecord(existingRec);
                        existingRec.setAlbumCover(incomingCover);
                    } else {
                        // Just update image URL
                        existingCover.setImageUrl(incomingCover.getImageUrl());
                    }
                }

                return recordRepository.save(existingRec);
            }
        }

        // New record path
        if (record.getAlbumCover() != null) {
            record.getAlbumCover().setRecord(record);
        }

        return recordRepository.save(record);
    }

    public Record createRecordFromSpotifyDTO(SpotifyTrackDTO dto) {
        // Find or create Artist
        Artist artist = artistRepository.findByName(dto.getArtistName())
                .orElseGet(() -> {
                    Artist newArtist = new Artist();
                    newArtist.setName(dto.getArtistName());
                    newArtist.setBio("Imported from Spotify");
                    newArtist.setCountry("Unknown");
                    return artistRepository.save(newArtist);
                });

        // Map genres
        Set<Genre> genreSet = new HashSet<>();
        if (dto.getGenres() != null) {
            for (String genreName : dto.getGenres()) {
                Genre genre = genreRepository.findByName(genreName)
                        .orElseGet(() -> genreRepository.save(new Genre(null, genreName)));
                genreSet.add(genre);
            }
        }

        // Create or update record
        Record record = recordRepository.findBySpotifyTrackId(dto.getId()).orElse(new Record());
        record.setTitle(dto.getName());
        record.setSpotifyTrackId(dto.getId());
        record.setPreviewUrl(dto.getPreviewUrl());
        record.setArtist(artist);
        record.setGenres(genreSet);

        // Handle album cover
        if (dto.getCoverImageUrl() != null && !dto.getCoverImageUrl().isEmpty()) {
            AlbumCover cover = record.getAlbumCover();
            if (cover == null) {
                cover = new AlbumCover();
                cover.setImageUrl(dto.getCoverImageUrl());
                cover.setRecord(record);
                record.setAlbumCover(cover);
            } else {
                cover.setImageUrl(dto.getCoverImageUrl());
            }
        }

        return createOrUpdateRecord(record);
    }
}
