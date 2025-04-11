package com.lola.vinyljukebox.repositories;

import com.lola.vinyljukebox.entities.Record;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecordRepository extends JpaRepository<Record, Long> {
    Optional<Record> findBySpotifyTrackId(String spotifyTrackId);
}
