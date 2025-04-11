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
