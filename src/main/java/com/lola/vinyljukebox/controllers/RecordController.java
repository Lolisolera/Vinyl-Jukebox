package com.lola.vinyljukebox.controllers;

import com.lola.vinyljukebox.dto.SpotifyTrackDTO;
import com.lola.vinyljukebox.entities.Record;
import com.lola.vinyljukebox.services.RecordService;
import com.lola.vinyljukebox.services.SpotifyIntegrationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/records")
public class RecordController {

    private final RecordService recordService;
    private final SpotifyIntegrationService spotifyService;

    public RecordController(RecordService recordService, SpotifyIntegrationService spotifyService) {
        this.recordService = recordService;
        this.spotifyService = spotifyService;
    }

    // GET: Search Spotify (keep this above the generic /{id} mapping)
    @GetMapping("/spotify-search")
    public List<SpotifyTrackDTO> searchSpotifyTracks(@RequestParam String query) {
        return spotifyService.searchTracks(query);
    }

    // POST: Add record from Spotify (refactored)
    @PostMapping("/spotify-add")
    public Record createRecordFromSpotify(@RequestParam String trackId) {
        SpotifyTrackDTO dto = spotifyService.getTrackById(trackId);
        return recordService.createRecordFromSpotifyDTO(dto);
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
