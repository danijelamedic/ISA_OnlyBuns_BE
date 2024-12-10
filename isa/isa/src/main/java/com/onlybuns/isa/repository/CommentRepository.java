package com.onlybuns.isa.repository;

import com.onlybuns.isa.model.Comment;
import com.onlybuns.isa.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    public List<Comment> findByPost(Post post);
}
