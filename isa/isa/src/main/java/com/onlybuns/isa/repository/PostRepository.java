package com.onlybuns.isa.repository;

import com.onlybuns.isa.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    // Ova metoda je automatski dostupna jer JpaRepository sadrži implementaciju za findAll()
}
