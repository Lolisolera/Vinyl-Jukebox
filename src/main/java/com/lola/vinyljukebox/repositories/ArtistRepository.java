package com.lola.vinyljukebox.repositories;

import com.lola.vinyljukebox.entities.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistRepository extends JpaRepository<Artist, Long> {

}