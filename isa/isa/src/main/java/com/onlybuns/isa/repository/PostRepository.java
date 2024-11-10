package com.onlybuns.isa.repository;

import com.onlybuns.isa.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PostRepository extends JpaRepository<Post, Long> {
    public void deleteById(long id);
}
