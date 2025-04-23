package com.lola.vinyljukebox.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    private String password;
    private String email;

}
//Using Lombok (@Getter, @Setter, etc.) to avoid boilerplate.
//Using the Jakarta Persistence imports on Spring Boot 3+ (jakarta.persistence.*).
