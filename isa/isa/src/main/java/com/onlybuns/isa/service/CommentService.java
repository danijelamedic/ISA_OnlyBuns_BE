package com.onlybuns.isa.service;

import com.onlybuns.isa.dto.CommentDto;
import com.onlybuns.isa.model.Comment;
import com.onlybuns.isa.model.Post;
import com.onlybuns.isa.model.User;
import com.onlybuns.isa.repository.CommentRepository;
import com.onlybuns.isa.repository.PostRepository;
import com.onlybuns.isa.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RateLimiter rateLimiter;

    public List<Comment> findByPost(Post post) {
        return commentRepository.findByPost(post);
    }
    public List<Comment> findByUserId(long userId) { return commentRepository.findByUserId(userId); }
    public int countCommentsPerMonth(int month, int year){ return commentRepository.countPerMonth(month,year);}
    public int countCommentsPerWeek(int week, int year){ return commentRepository.countPerWeek(week,year);}
    public int countCommentsPerYear(int year){ return commentRepository.countPerYear(year);}

    // NOVO: metod za kreiranje komentara
    @Transactional
    public Comment addComment(Long userId, Long postId, String content) {
        if (!rateLimiter.allowRequest(userId)) {
            throw new RuntimeException("Prekoračen broj komentara");
        }

        User user = userRepository.findById(userId).orElseThrow();
        Post post = postRepository.findById(postId).orElseThrow();

        System.out.println("komentarisem.....");
        System.out.println("userId je: " + userId);
        Comment comment = new Comment();
        comment.setUser(user);
        comment.setPost(post);
        comment.setContent(content);
        comment.setCreationTime(LocalDateTime.now());

        user.setLastActivityTime(LocalDateTime.now());
        commentRepository.save(comment); // samo ovo mi treba
        userRepository.save(user);
        return comment;
    }


    public List<CommentDto> getCommentsForPost(Long postId) {
        List<Comment> comments = commentRepository.findByPostIdOrderByCreationTimeAsc(postId);
        return comments.stream().map(this::toDto).collect(Collectors.toList());
    }


    private CommentDto toDto(Comment comment) {
        CommentDto dto = new CommentDto();
        dto.setId(comment.getId());
        dto.setContent(comment.getContent());
        dto.setCreationTime(comment.getCreationTime());
        dto.setPostId(comment.getPost().getId());
        dto.setUserId(comment.getUser().getId());
        dto.setUsername(comment.getUser().getUsername()); // ako ti treba username na frontu
        return dto;
    }

    public int countNewComments(Long userId, LocalDateTime since) {
        if (since == null) {
            return 0;  // ili možeš vratiti ukupno komentara ako želiš
        }
        return commentRepository.countByPostUserIdAndCreationTimeAfter(userId, since);
    }
}
