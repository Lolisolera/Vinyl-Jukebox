package com.lola.vinyljukebox.dto;

import com.lola.vinyljukebox.entities.AlbumCover;
import com.lola.vinyljukebox.entities.Artist;
import com.lola.vinyljukebox.entities.Genre;
import com.lola.vinyljukebox.entities.Record;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class RecordDTO {

    private Long id;
    private String title;
    private String previewUrl;
    private String deezerTrackId;
    private String artistName;
    private List<String> genres;
    private String albumCoverUrl;

    public RecordDTO(Record record) {
        this.id = record.getId();
        this.title = record.getTitle();
        this.previewUrl = record.getPreviewUrl();
        this.deezerTrackId = record.getDeezerTrackId();

        Artist artist = record.getArtist();
        this.artistName = (artist != null) ? artist.getName() : "Unknown";

        this.genres = (record.getGenres() != null)
                ? record.getGenres().stream().map(Genre::getName).collect(Collectors.toList())
                : Collections.emptyList();

        AlbumCover cover = record.getAlbumCover();
        this.albumCoverUrl = (cover != null) ? cover.getImageUrl() : null;
    }

    // Getters
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getPreviewUrl() { return previewUrl; }
    public String getDeezerTrackId() { return deezerTrackId; }
    public String getArtistName() { return artistName; }
    public List<String> getGenres() { return genres; }
    public String getAlbumCoverUrl() { return albumCoverUrl; }
}
