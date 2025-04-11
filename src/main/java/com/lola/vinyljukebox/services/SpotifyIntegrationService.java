package com.lola.vinyljukebox.services;

import com.lola.vinyljukebox.dto.SpotifyTrackDTO;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;


@Service
public class SpotifyIntegrationService {

    // Possibly load credentials from application.properties or environment variables
    @Value("${spotify.client-id}")
    private String clientId;
    @Value("${spotify.client-secret}")
    private String clientSecret;

    // e.g. "https://api.spotify.com/v1"
    private static final String SPOTIFY_API_BASE_URL = "https://api.spotify.com/v1";

    public List<SpotifyTrackDTO> searchTracks(String query) {
        // 1) Acquire token from Spotify (if needed)
        // 2) Perform GET /search with the 'q' parameter
        // 3) Map JSON to List<SpotifyTrackDTO>
        return List.of();
    }

    public SpotifyTrackDTO getTrackById(String spotifyTrackId) {
        // 1) Acquire token from Spotify
        // 2) Perform GET /tracks/{spotifyTrackId}
        // 3) Map JSON to SpotifyTrackDTO
        return null;
    }
}

