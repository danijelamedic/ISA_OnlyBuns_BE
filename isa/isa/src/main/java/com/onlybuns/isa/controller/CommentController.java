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
    public ResponseEntity<CommentDto> addComment(@RequestBody CommentDto commentDto) {
        Long userId = commentDto.getUserId();
        Long postId = commentDto.getPostId();
        String content = commentDto.getContent();

        Comment savedComment = commentService.addComment(userId, postId, content);

        CommentDto responseDto = new CommentDto();
        responseDto.setId(savedComment.getId());
        responseDto.setUserId(savedComment.getUser().getId());
        responseDto.setUsername(savedComment.getUser().getUsername()); // ako imaš
        responseDto.setPostId(savedComment.getPost().getId());
        responseDto.setContent(savedComment.getContent());
        responseDto.setCreationTime(savedComment.getCreationTime());

        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentDto>> getCommentsForPost(@PathVariable Long postId) {
        List<CommentDto> comments = commentService.getCommentsForPost(postId);
        return ResponseEntity.ok(comments);
    }

}