package com.lola.vinyljukebox.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @JsonManagedReference // Artist -> Records
    private Artist artist;

    @ManyToMany
    @JoinTable(
            name = "record_genres",
            joinColumns = @JoinColumn(name = "record_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private Set<Genre> genres;

    @OneToOne(mappedBy = "record", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference // Prevent infinite loop on AlbumCover -> Record -> AlbumCover...
    private AlbumCover albumCover;

    @Column(name = "deezer_track_id", unique = true, length = 512)
    private String deezerTrackId;

    @Column(length = 1024)
    private String previewUrl;


    // Optional helper method to set both sides of the relationship
    public void setAlbumCover(AlbumCover albumCover) {
        this.albumCover = albumCover;
        if (albumCover != null) {
            albumCover.setRecord(this);
        }
    }
}
