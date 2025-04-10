package com.lola.vinyljukebox.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "album_covers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlbumCover {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageUrl;

    @OneToOne
    @JoinColumn(name = "record_id")
    private Record record;
}
//@JoinColumn(name = "record_id") ensures the AlbumCover table has a column referencing the Record it belongs to.