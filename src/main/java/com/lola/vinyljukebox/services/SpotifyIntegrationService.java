package com.lola.vinyljukebox.services;

import com.lola.vinyljukebox.dto.SpotifyTrackDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@Service
public class SpotifyIntegrationService {

    @Value("${spotify.client-id}")
    private String clientId;

    @Value("${spotify.client-secret}")
    private String clientSecret;

    private static final String SPOTIFY_API_BASE_URL = "https://api.spotify.com/v1";

    public List<SpotifyTrackDTO> searchTracks(String query) {
        String token = getAccessToken();

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        String url = SPOTIFY_API_BASE_URL + "/search?q=" + query + "&type=track&limit=5";

        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);

        List<SpotifyTrackDTO> results = new ArrayList<>();
        if (response.getStatusCode() == HttpStatus.OK) {
            Map<String, Object> body = response.getBody();
            if (body != null && body.containsKey("tracks")) {
                Map<String, Object> tracks = (Map<String, Object>) body.get("tracks");
                List<Map<String, Object>> items = (List<Map<String, Object>>) tracks.get("items");

                for (Map<String, Object> item : items) {
                    SpotifyTrackDTO dto = new SpotifyTrackDTO();
                    dto.setId((String) item.get("id"));
                    dto.setName((String) item.get("name"));
                    dto.setPreviewUrl((String) item.get("preview_url"));

                    List<Map<String, Object>> artists = (List<Map<String, Object>>) item.get("artists");
                    if (!artists.isEmpty()) {
                        dto.setArtistName((String) artists.get(0).get("name"));
                    }
                    results.add(dto);
                }
            }
        }
        return results;
    }

    public SpotifyTrackDTO getTrackById(String spotifyTrackId) {
        // 1) Acquire token from Spotify
        // 2) Perform GET /tracks/{spotifyTrackId}
        // 3) Map JSON to SpotifyTrackDTO
        return null;
    }

    /**
     * Private helper method to fetch an access token from Spotify using Client Credentials Flow.
     */
    private String getAccessToken() {
        String authEndpoint = "https://accounts.spotify.com/api/token";

        RestTemplate restTemplate = new RestTemplate();

        String credentials = clientId + ":" + clientSecret;
        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "Basic " + encodedCredentials);

        HttpEntity<String> request = new HttpEntity<>("grant_type=client_credentials", headers);

        ResponseEntity<Map> response = restTemplate.exchange(authEndpoint, HttpMethod.POST, request, Map.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return (String) response.getBody().get("access_token");
        }

        throw new RuntimeException("Failed to fetch Spotify access token");
    }
}

