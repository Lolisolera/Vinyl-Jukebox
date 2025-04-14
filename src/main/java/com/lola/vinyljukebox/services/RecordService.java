package com.lola.vinyljukebox.services;

import com.lola.vinyljukebox.dto.SpotifyTrackDTO;
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
                return recordRepository.save(existingRec);
            }
        }
        return recordRepository.save(record);
    }

    public Record createRecordFromSpotifyDTO(SpotifyTrackDTO dto) {
        // Find or create artist
        Artist artist = artistRepository.findByName(dto.getArtistName())
                .orElseGet(() -> {
                    Artist newArtist = new Artist();
                    newArtist.setName(dto.getArtistName());
                    newArtist.setBio("Imported from Spotify");
                    newArtist.setCountry("Unknown");
                    return artistRepository.save(newArtist);
                });

        // Map genres to Set<Genre>
        Set<Genre> genreSet = new HashSet<>();
        if (dto.getGenres() != null) {
            for (String genreName : dto.getGenres()) {
                Genre genre = genreRepository.findByName(genreName)
                        .orElseGet(() -> genreRepository.save(new Genre(null, genreName)));
                genreSet.add(genre);
            }
        }

        // Build and save record
        Record record = new Record();
        record.setTitle(dto.getName());
        record.setSpotifyTrackId(dto.getId());
        record.setPreviewUrl(dto.getPreviewUrl());
        record.setArtist(artist);
        record.setGenres(genreSet);

        return createOrUpdateRecord(record);
    }
}
