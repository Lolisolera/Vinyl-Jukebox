package com.lola.vinyljukebox.services;

import com.lola.vinyljukebox.entities.Record;
import com.lola.vinyljukebox.repositories.RecordRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecordService {
    private final RecordRepository recordRepository;

    public RecordService(RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
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
                return recordRepository.save(existingRec);
            }
        }
        return recordRepository.save(record);
    }
}
