package com.lola.vinyljukebox.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "collections")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecordCollection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Record record;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateAdded;
}
//A bridging entity to ensure user’s custom “collection” of records.
