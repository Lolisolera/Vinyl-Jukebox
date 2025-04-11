package com.lola.vinyljukebox.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "records")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private Integer releaseYear;

    @ManyToOne
    @JoinColumn(name = "artist_id")
    private Artist artist;

    @ManyToMany
    @JoinTable(
            name = "record_genres",
            joinColumns = @JoinColumn(name = "record_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private Set<Genre> genres;

    @OneToOne(mappedBy = "record", cascade = CascadeType.ALL)
    private AlbumCover albumCover;

    @Column(name = "spotify_track_id", unique = true)
    private String spotifyTrackId;

    private String previewUrl; // ⭐️ NEW! URL to 30-second Spotify clip
}
