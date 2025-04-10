package com.lola.vinyljukebox.repositories;

import com.lola.vinyljukebox.entities.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {

}
