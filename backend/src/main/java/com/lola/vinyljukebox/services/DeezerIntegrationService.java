package com.lola.vinyljukebox.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lola.vinyljukebox.dto.DeezerTrackDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
@SuppressWarnings("unchecked")
public class DeezerIntegrationService {

    private static final String DEEZER_API_BASE_URL = "https://api.deezer.com";

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<DeezerTrackDTO> searchTracks(String query) {
        String title = query;
        String artist = null;

        if (query.contains(" ")) {
            int lastSpace = query.lastIndexOf(" ");
            title = query.substring(0, lastSpace);
            artist = query.substring(lastSpace + 1);
        }

        String encodedQuery;
        if (artist != null) {
            encodedQuery = URLEncoder.encode("track:\"" + title + "\" artist:\"" + artist + "\"", StandardCharsets.UTF_8);
        } else {
            encodedQuery = URLEncoder.encode("track:\"" + title + "\"", StandardCharsets.UTF_8);
        }

        String url = DEEZER_API_BASE_URL + "/search?q=" + encodedQuery;

        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
        List<DeezerTrackDTO> results = new ArrayList<>();

        if (response.getStatusCode().is2xxSuccessful()) {
            Map<String, Object> body = response.getBody();
            if (body != null && body.containsKey("data")) {
                List<Map<String, Object>> items = (List<Map<String, Object>>) body.get("data");

                for (Map<String, Object> item : items) {
                    String previewUrl = (String) item.get("preview");
                    String titleValue = (String) item.get("title");

                    Map<String, Object> artistMap = (Map<String, Object>) item.get("artist");
                    Map<String, Object> album = (Map<String, Object>) item.get("album");
                    String artistName = artistMap != null ? (String) artistMap.get("name") : null;
                    String albumCoverUrl = album != null ? (String) album.get("cover_medium") : null;

                    // ✅ Skip tracks missing required fields
                    if (previewUrl == null || previewUrl.isEmpty() || !previewUrl.endsWith(".mp3")) {
                        continue;
                    }

                    DeezerTrackDTO dto = new DeezerTrackDTO();
                    dto.setId(String.valueOf(item.get("id")));
                    dto.setTitle(titleValue != null ? titleValue : "Unknown Title");
                    dto.setPreviewUrl(previewUrl);
                    dto.setArtistName(artistName != null ? artistName : "Unknown Artist");
                    dto.setAlbumCoverUrl(albumCoverUrl != null ? albumCoverUrl : "/default-cover.jpg");
                    dto.setGenres(new ArrayList<>()); // Deezer doesn't return genres in track search

                    results.add(dto);
                }
            }
        }

        return results;
    }

    public DeezerTrackDTO getTrackById(String deezerTrackId) {
        String url = DEEZER_API_BASE_URL + "/track/" + deezerTrackId;

        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            Map<String, Object> body = response.getBody();
            DeezerTrackDTO dto = new DeezerTrackDTO();

            dto.setId(String.valueOf(body.get("id")));
            dto.setTitle((String) body.get("title"));
            dto.setPreviewUrl((String) body.get("preview"));

            Map<String, Object> artist = (Map<String, Object>) body.get("artist");
            if (artist != null) {
                dto.setArtistName((String) artist.get("name"));
            }

            Map<String, Object> album = (Map<String, Object>) body.get("album");
            if (album != null) {
                dto.setAlbumCoverUrl((String) album.get("cover_medium"));
            }

            dto.setGenres(new ArrayList<>()); // Optional: extend with artist/album genre fetching

            return dto;
        }

        return null;
    }
}
