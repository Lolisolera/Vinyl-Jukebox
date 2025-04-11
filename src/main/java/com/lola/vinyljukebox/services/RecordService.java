package com.lola.vinyljukebox.services;

import com.lola.vinyljukebox.repositories.RecordRepository;
import org.springframework.stereotype.Service;
import com.lola.vinyljukebox.entities.Record;

import java.util.Optional;
import java.util.List;

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
            // e.g. check if there's an existing record
            Optional<Record> existing = recordRepository.findBySpotifyTrackId(record.getSpotifyTrackId());
            if (existing.isPresent()) {
                // update logic
                Record existingRec = existing.get();
                existingRec.setTitle(record.getTitle());
                existingRec.setArtist(record.getArtist());
                // etc.
                return recordRepository.save(existingRec);
            }
        }

        return recordRepository.save(record);
    }
}
