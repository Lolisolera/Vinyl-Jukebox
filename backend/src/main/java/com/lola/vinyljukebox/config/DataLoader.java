package com.lola.vinyljukebox.config;

import com.lola.vinyljukebox.dto.DeezerTrackDTO;
import com.lola.vinyljukebox.entities.Record;
import com.lola.vinyljukebox.services.RecordService;
import com.lola.vinyljukebox.services.DeezerIntegrationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final DeezerIntegrationService deezerIntegrationService;
    private final RecordService recordService;

    public DataLoader(DeezerIntegrationService deezerIntegrationService, RecordService recordService) {
        this.deezerIntegrationService = deezerIntegrationService;
        this.recordService = recordService;
    }

    @Override
    public void run(String... args) {
        System.out.println("🎧 Importing sample tracks from Deezer...");

        List<String> sampleTrackQueries = List.of(
                "Borderline Tame Impala",
                "Hey Joe Charlotte Gainsbourg",
                "Thriller Michael Jackson",
                "Panique Juniore",
                "Sexy Boy Air"
        );

        for (String query : sampleTrackQueries) {
            List<DeezerTrackDTO> results = deezerIntegrationService.searchTracks(query);

            if (!results.isEmpty()) {
                DeezerTrackDTO dto = results.get(0); // Pick the first result
                Record saved = recordService.createRecordFromDeezerDTO(dto);
                System.out.println("Imported: " + saved.getTitle() + " by " + saved.getArtist().getName());
            } else {
                System.out.println("No Deezer result for: " + query);
            }
        }

        System.out.println("DataLoader: Deezer tracks loaded!");
    }
}
