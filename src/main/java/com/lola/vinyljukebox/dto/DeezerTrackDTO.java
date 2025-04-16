package com.lola.vinyljukebox.dto;

import lombok.Data;

import java.util.List;

@Data
public class DeezerTrackDTO {
    private String id;
    private String title;
    private String artistName;
    private String previewUrl;
    private String albumCoverUrl;
    private List<String> genres;
}
