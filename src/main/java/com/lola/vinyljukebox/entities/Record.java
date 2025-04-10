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

    private String title;
    private Integer releaseYear;

    @ManyToOne
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
}
//@ManyToMany with a JoinTable to link Record and Genre.
//@OneToOne for AlbumCover, referencing the record field on the other side.