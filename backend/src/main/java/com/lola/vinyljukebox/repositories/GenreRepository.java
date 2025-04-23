package com.lola.vinyljukebox.repositories;

import com.lola.vinyljukebox.entities.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GenreRepository extends JpaRepository<Genre, Long> {

    Optional<Genre> findByName(String genreName);
}
