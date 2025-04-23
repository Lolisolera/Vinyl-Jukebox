package com.lola.vinyljukebox.repositories;

import com.lola.vinyljukebox.entities.RecordCollection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordCollectionRepository extends JpaRepository<RecordCollection, Long> {

}