package com.onlybuns.isa.repository;

import com.onlybuns.isa.dto.PostDto;
import com.onlybuns.isa.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PostRepository extends JpaRepository<Post, Long> {
    public void deleteById(long id);
    public List<Post> findByUserId(long id);
    List<Post> findAllByOrderByCreationTimeDesc();

}
