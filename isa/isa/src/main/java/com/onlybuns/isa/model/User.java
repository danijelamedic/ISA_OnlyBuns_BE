package com.onlybuns.isa.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;

    // ... Getters and Setters ...

    @OneToMany(mappedBy = "user")
    private List<Post> posts;  // Korisnik ima objave

    @OneToMany(mappedBy = "user")
    private List<Comment> comments;  // Korisnik ima komentare
}
