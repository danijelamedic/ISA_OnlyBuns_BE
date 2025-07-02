package com.onlybuns.isa.service;

import com.onlybuns.isa.model.Comment;
import com.onlybuns.isa.model.Post;
import com.onlybuns.isa.model.User;
import com.onlybuns.isa.repository.CommentRepository;
import com.onlybuns.isa.repository.PostRepository;
import com.onlybuns.isa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Comment> findByPost(Post post) {
        return commentRepository.findByPost(post);
    }
    public List<Comment> findByUserId(long userId) { return commentRepository.findByUserId(userId); }
    public int countCommentsPerMonth(int month, int year){ return commentRepository.countPerMonth(month,year);}
    public int countCommentsPerWeek(int week, int year){ return commentRepository.countPerWeek(week,year);}
    public int countCommentsPerYear(int year){ return commentRepository.countPerYear(year);}
    // NOVO: metod za kreiranje komentara
    public Comment addComment(Long userId, Long postId, String content) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        Comment comment = new Comment();
        comment.setUser(user);
        comment.setPost(post);
        comment.setContent(content);
        comment.setCreationTime(java.time.LocalDateTime.now());

        return commentRepository.save(comment);
    }
}
