package com.onlybuns.isa.service;

import com.onlybuns.isa.model.Comment;
import com.onlybuns.isa.model.Post;
import com.onlybuns.isa.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    public List<Comment> findByPost(Post post) {
        return commentRepository.findByPost(post);
    }
}