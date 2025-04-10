package com.lola.vinyljukebox.repositories;

import com.lola.vinyljukebox.entities.Record;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordRepository extends JpaRepository<Record, Long> {

}
