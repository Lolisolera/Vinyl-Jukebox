package com.lola.vinyljukebox.controllers;

import com.lola.vinyljukebox.dto.RecordDTO;
import com.lola.vinyljukebox.dto.DeezerTrackDTO;
import com.lola.vinyljukebox.entities.Record;
import com.lola.vinyljukebox.services.RecordService;
import com.lola.vinyljukebox.services.DeezerIntegrationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/records")
@CrossOrigin(origins = {
        "http://localhost:5173",
        "https://vinyl-jukebox.netlify.app"
})
public class RecordController {

    private final RecordService recordService;
    private final DeezerIntegrationService deezerService;

    public RecordController(
            RecordService recordService,
            DeezerIntegrationService deezerService
    ) {
        this.recordService = recordService;
        this.deezerService = deezerService;
    }

    // GET: Search Deezer
    @GetMapping("/deezer-search")
    public List<DeezerTrackDTO> searchDeezerTracks(@RequestParam String query) {
        return deezerService.searchTracks(query);
    }

    // POST: Add record from Deezer
    @PostMapping("/deezer-add")
    public ResponseEntity<?> createRecordFromDeezer(@RequestParam String trackId) {
        try {
            DeezerTrackDTO dto = deezerService.getTrackById(trackId);
            Record record = recordService.createRecordFromDeezerDTO(dto);
            return ResponseEntity.ok(record);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Deezer track not found for ID: " + trackId);
        }
    }

    // GET: List all records
    @GetMapping
    public ResponseEntity<List<RecordDTO>> getAllRecords() {
        List<Record> records = recordService.getAllRecords();
        List<RecordDTO> dtoList = records.stream()
                .map(RecordDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }

    // GET: Specific record by ID
    @GetMapping("/{id}")
    public ResponseEntity<RecordDTO> getRecordById(@PathVariable Long id) {
        Record record = recordService.getRecordById(id);
        if (record == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new RecordDTO(record));
    }

    // DELETE: Specific record by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRecord(@PathVariable Long id) {
        Record record = recordService.getRecordById(id);
        if (record == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Record not found for ID: " + id);
        }
        try {
            recordService.deleteRecord(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while deleting the record: " + e.getMessage());
        }
    }
}
