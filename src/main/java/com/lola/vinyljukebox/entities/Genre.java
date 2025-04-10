package com.lola.vinyljukebox.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "genres")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "genres")
    private Set<Record> records;
}
//The inverse side of @ManyToMany from Record.
//Using Set to avoid duplicates (and it’s typically more efficient for ManyToMany relations).