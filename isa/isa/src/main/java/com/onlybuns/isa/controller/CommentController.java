package com.onlybuns.isa.controller;

import com.onlybuns.isa.service.CommentService;
import com.onlybuns.isa.service.UserService;
import com.onlybuns.isa.dto.CommentDto;
import com.onlybuns.isa.model.Comment;
//import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        Long userId = commentDto.getUserId();
        Long postId = commentDto.getPostId();
        String content = commentDto.getContent();

        Comment savedComment = commentService.addComment(userId, postId, content);

        return ResponseEntity.ok(savedComment);
    }
}