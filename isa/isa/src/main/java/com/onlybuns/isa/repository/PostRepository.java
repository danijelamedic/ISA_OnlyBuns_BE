package com.onlybuns.isa.repository;

import com.onlybuns.isa.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PostRepository extends JpaRepository<Post, Long> {
    // Ako želite da dodate pretragu po korisniku
    List<Post> findByUserId(Long userId);

    // Pretraga po lokaciji
    List<Post> findByLocationId(Long locationId);
}