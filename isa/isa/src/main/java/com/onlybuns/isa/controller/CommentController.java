package com.onlybuns.isa.controller;

import com.onlybuns.isa.service.CommentService;
import com.onlybuns.isa.service.UserService;
import com.onlybuns.isa.dto.CommentDto;
import com.onlybuns.isa.model.Comment;
//import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@Tag(name="Comment controller", description = "The comment API")
@RestController
@RequestMapping("/api/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService; // Da možeš iz username dobiti ID

    @PostMapping
    public ResponseEntity<?> addComment(@RequestBody CommentDto commentDto) {
        try {
            Long userId = commentDto.getUserId();
            Long postId = commentDto.getPostId();
            String content = commentDto.getContent();

            Comment savedComment = commentService.addComment(userId, postId, content);

            CommentDto responseDto = new CommentDto();
            responseDto.setId(savedComment.getId());
            responseDto.setUserId(savedComment.getUser().getId());
            responseDto.setUsername(savedComment.getUser().getUsername());
            responseDto.setPostId(savedComment.getPost().getId());
            responseDto.setContent(savedComment.getContent());
            responseDto.setCreationTime(savedComment.getCreationTime());

            return ResponseEntity.ok(responseDto);

        } catch (RuntimeException e) {
            if (e.getMessage().contains("Prekoračen broj komentara")) {
                return ResponseEntity.status(429).body("Prekoračen broj komentara u poslednjih 1 minut");
            }
            return ResponseEntity.status(500).body("Došlo je do greške");
        }
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentDto>> getCommentsForPost(@PathVariable Long postId) {
        List<CommentDto> comments = commentService.getCommentsForPost(postId);
        return ResponseEntity.ok(comments);
    }

}