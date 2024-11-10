package com.onlybuns.isa.repository;

import com.onlybuns.isa.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
