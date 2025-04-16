/*package com.lola.vinyljukebox.dto;

import lombok.Getter;
import lombok.Setter;
import se.michaelthelin.spotify.model_objects.specification.ArtistSimplified;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.model_objects.specification.Image;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SpotifyTrackDTO {
    private String id;
    private String name;
    private String previewUrl;
    private String artistName;
    private List<String> genres;
    private String coverImageUrl;

    // No-args constructor added so you can call new SpotifyTrackDTO()
    public SpotifyTrackDTO() {
    }

    public SpotifyTrackDTO(Track track) {
        this.id = track.getId();
        this.name = track.getName();
        this.previewUrl = track.getPreviewUrl();

        ArtistSimplified[] artists = track.getArtists();
        this.artistName = (artists.length > 0) ? artists[0].getName() : "Unknown";

        this.genres = new ArrayList<>();

        Image[] images = track.getAlbum().getImages();
        this.coverImageUrl = (images.length > 0) ? images[0].getUrl() : null;
    }
}

*/


// This DTO (Data Transfer Object) is used to represent the simplified track information retrieved from the Spotify API,
// including fields like ID, name, preview URL, and artist name. It's used to map Spotify JSON responses into Java objects
// that can be handled easily within the application when searching or adding tracks via the Spotify integration.