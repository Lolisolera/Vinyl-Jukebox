package com.lola.vinyljukebox.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpotifyTrackDTO {
    private String id;
    private String name;
    private String previewUrl;
    private String artistName;
}
// This DTO (Data Transfer Object) is used to represent the simplified track information retrieved from the Spotify API,
// including fields like ID, name, preview URL, and artist name. It's used to map Spotify JSON responses into Java objects
// that can be handled easily within the application when searching or adding tracks via the Spotify integration.