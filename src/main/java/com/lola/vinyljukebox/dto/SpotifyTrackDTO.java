package com.lola.vinyljukebox.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SpotifyTrackDTO {
    private String id;
    private String name;
    private String previewUrl;
    private String artistName;
    private List<String> genres; // holds genre names associated with the artist

    public void setCoverImageUrl(String coverUrl) {
    }

    public String getCoverImageUrl() {
        return "";
    }
}

// This DTO (Data Transfer Object) is used to represent the simplified track information retrieved from the Spotify API,
// including fields like ID, name, preview URL, and artist name. It's used to map Spotify JSON responses into Java objects
// that can be handled easily within the application when searching or adding tracks via the Spotify integration.