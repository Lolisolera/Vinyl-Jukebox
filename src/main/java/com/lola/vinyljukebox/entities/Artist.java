package com.lola.vinyljukebox.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "artists")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String bio;
    private String country;

    @OneToMany(mappedBy = "artist")
    private List<Record> records;
}

//@OneToMany(mappedBy = "artist") ties back to the Record entity’s @ManyToOne private Artist artist;