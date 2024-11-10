package com.onlybuns.isa.controller;

import com.onlybuns.isa.dto.CommentDto;
import com.onlybuns.isa.dto.PostDto;
import com.onlybuns.isa.dto.UserDto;
import com.onlybuns.isa.model.Comment;
import com.onlybuns.isa.model.Post;
import com.onlybuns.isa.model.User;
import com.onlybuns.isa.service.LikeService;
import com.onlybuns.isa.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Tag(name="Post controller", description = "The post API")
@RestController
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private LikeService likeService;

    @GetMapping
    public ResponseEntity<List<PostDto>> getPosts() {
        List<Post> posts = postService.findAll();

        List<PostDto> postsDtos = new ArrayList<>();
        for (Post post : posts) {

            postsDtos.add(new PostDto(post));
        }
        return new ResponseEntity<>(postsDtos, HttpStatus.OK);
    }

    @Operation(description = "Create new post", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Post.class)) }),
            @ApiResponse(responseCode = "409", description = "Not possible to create new post when given id is not null or empty",
                    content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Post> createPost(@Valid @RequestBody PostDto postDto) {
        try {
            Post savedPost = postService.create(postDto);
            return new ResponseEntity<>(savedPost, HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // handle not found exceptions
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);  // handle generic errors
        }
    }
}