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

    public int countCommentsPerMonth(int month, int year){ return commentRepository.countPerMonth(month,year);}
    public int countCommentsPerWeek(int week, int year){ return commentRepository.countPerWeek(week,year);}
    public int countCommentsPerYear(int year){ return commentRepository.countPerYear(year);}
}
