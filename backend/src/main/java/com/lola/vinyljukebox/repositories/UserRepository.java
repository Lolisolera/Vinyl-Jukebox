package com.lola.vinyljukebox.repositories;

import com.lola.vinyljukebox.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
