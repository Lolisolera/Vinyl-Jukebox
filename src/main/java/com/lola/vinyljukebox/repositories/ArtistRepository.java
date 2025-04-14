package com.lola.vinyljukebox.repositories;

import com.lola.vinyljukebox.entities.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArtistRepository extends JpaRepository<Artist, Long> {

    Optional<Artist> findByName(String artistName);
}