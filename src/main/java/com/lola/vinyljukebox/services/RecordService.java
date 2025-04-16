package com.lola.vinyljukebox.services;

import com.lola.vinyljukebox.dto.DeezerTrackDTO;
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
        if (record.getDeezerTrackId() != null) {
            Optional<Record> existing = recordRepository.findByDeezerTrackId(record.getDeezerTrackId());
            if (existing.isPresent()) {
                Record existingRec = existing.get();
                existingRec.setTitle(record.getTitle());
                existingRec.setArtist(record.getArtist());

                if (record.getPreviewUrl() != null && !record.getPreviewUrl().isEmpty()) {
                    existingRec.setPreviewUrl(record.getPreviewUrl());
                }

                existingRec.setGenres(record.getGenres());

                AlbumCover incomingCover = record.getAlbumCover();
                if (incomingCover != null) {
                    AlbumCover existingCover = existingRec.getAlbumCover();
                    if (existingCover == null) {
                        incomingCover.setRecord(existingRec);
                        existingRec.setAlbumCover(incomingCover);
                    } else {
                        existingCover.setImageUrl(incomingCover.getImageUrl());
                    }
                }

                return recordRepository.save(existingRec);
            }
        }

        if (record.getAlbumCover() != null) {
            record.getAlbumCover().setRecord(record);
        }

        return recordRepository.save(record);
    }

    public Record createRecordFromDeezerDTO(DeezerTrackDTO dto) {
        Artist artist = artistRepository.findByName(dto.getArtistName())
                .orElseGet(() -> {
                    Artist newArtist = new Artist();
                    newArtist.setName(dto.getArtistName());
                    newArtist.setBio("Imported from Deezer");
                    newArtist.setCountry("Unknown");
                    return artistRepository.save(newArtist);
                });

        Set<Genre> genreSet = new HashSet<>();
        if (dto.getGenres() != null) {
            for (String genreName : dto.getGenres()) {
                Genre genre = genreRepository.findByName(genreName)
                        .orElseGet(() -> genreRepository.save(new Genre(null, genreName)));
                genreSet.add(genre);
            }
        }

        Record record = recordRepository.findByDeezerTrackId(dto.getId()).orElse(new Record());
        record.setTitle(dto.getTitle());
        record.setDeezerTrackId(dto.getId());

        if (dto.getPreviewUrl() != null && !dto.getPreviewUrl().isEmpty()) {
            record.setPreviewUrl(dto.getPreviewUrl());
        }

        record.setArtist(artist);
        record.setGenres(genreSet);

        // ✅ Enhanced album cover setting logic
        if (dto.getAlbumCoverUrl() != null && !dto.getAlbumCoverUrl().isEmpty()) {
            AlbumCover cover = record.getAlbumCover();
            if (cover == null) {
                cover = new AlbumCover();
                cover.setImageUrl(dto.getAlbumCoverUrl());
                cover.setRecord(record);
                record.setAlbumCover(cover);
            } else {
                cover.setImageUrl(dto.getAlbumCoverUrl());
            }
        } else {
            System.out.println("⚠️ Missing album cover URL for: " + dto.getTitle());
        }

        return createOrUpdateRecord(record);
    }
}
