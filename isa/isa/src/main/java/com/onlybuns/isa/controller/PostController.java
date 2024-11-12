package com.onlybuns.isa.controller;

import com.onlybuns.isa.dto.CommentDto;
import com.onlybuns.isa.dto.PostDto;
import com.onlybuns.isa.dto.UpdatePostDto;
import com.onlybuns.isa.model.Comment;
import com.onlybuns.isa.model.Post;
import com.onlybuns.isa.service.LikeService;
import com.onlybuns.isa.service.LocationService;
import com.onlybuns.isa.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@Tag(name="Post controller", description = "The post API")
@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "http://localhost:4200")
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private LikeService likeService;
    @Autowired
    private LocationService locationService;

    @Operation(description = "Get all posts", method = "GET")
    @GetMapping
    public ResponseEntity<List<PostDto>> getPosts(){
        List<Post> posts = postService.findAll();

        List<PostDto> postsDtos = new ArrayList<>();
        for (Post post : posts) {

            postsDtos.add(new PostDto(post));
        }
        return new ResponseEntity<>(postsDtos, HttpStatus.OK);
    }

    @Operation(description = "Returns posts by id", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "found post by id",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Post.class))),
            @ApiResponse(responseCode = "404", description = "post not found", content = @Content)
    })
    @GetMapping(value = "/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Long postId){
        Post post = postService.findById(postId);
        PostDto postDto = new PostDto(post);
        return new ResponseEntity<>(postDto, HttpStatus.OK);
    }

    @Operation(description = "Returns likes number by post id", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "found likes num by post id",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Post.class))),
            @ApiResponse(responseCode = "404", description = "likes num not found", content = @Content)
    })
    @GetMapping(value = "/getLikes/{postId}")
    public int getLikesNumber(@PathVariable Long postId){
        Post post = postService.findById(postId);
        return post.getLikes().size();
    }

    @Operation(description = "Returns comments by post id", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "found comments by post id",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Post.class))),
            @ApiResponse(responseCode = "404", description = "comments not found", content = @Content)
    })
    @GetMapping(value = "/getComments/{postId}")
    public ResponseEntity<List<CommentDto>> getComments(@PathVariable Long postId){
        Post post = postService.findById(postId);
        List<Comment> comments = post.getComments();
        List<CommentDto> commentsDtos = new ArrayList<>();
        for (Comment comment : comments) {
            commentsDtos.add(new CommentDto(comment));
        }
        return new ResponseEntity<>(commentsDtos, HttpStatus.OK);
    }

    @Operation(description = "Updates an existing post", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post successfully edited",
                    content =
                            { @Content(mediaType = "application/json", schema = @Schema(implementation = Post.class)) }
            ),
            @ApiResponse(responseCode = "404", description = "Post not found", content = @Content)
    })
    @PutMapping(consumes = "application/json")
    public ResponseEntity<PostDto> updatePost(@RequestBody UpdatePostDto updatePostDto){
        Post post = postService.findById(updatePostDto.getId());
        if (post == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(updatePostDto.getDescription() != null)
            post.setDescription(updatePostDto.getDescription());
        if(updatePostDto.getImagePath() != null)
            post.setImagePath(updatePostDto.getImagePath());
        post = postService.save(post);
        return new ResponseEntity<>(new PostDto(post), HttpStatus.OK);
    }

    @Operation(description = "Deletes a post", method = "DELETE")
    @ApiResponses(value = { @ApiResponse(responseCode = "404", description = "Post not found", content = @Content),
            @ApiResponse(responseCode = "204", description = "Post successfully deleted", content = @Content) } )

    @DeleteMapping(value = "/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId){
        try {
            Post post = postService.findById(postId);
            postService.deleteById(postId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            // Log error for debugging
            System.out.println("Error deleting post: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/getUsername/{postId}")
    public ResponseEntity<Map<String, String>> getUsername(@PathVariable Long postId){
        Post post = postService.findById(postId);
        if (post == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Map<String, String> response = new HashMap<>();
        response.put("username", post.getUser().getUsername());

        // Vraćaš mapu kao JSON objekat
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value="getByUser/{userId}")
    public ResponseEntity<List<PostDto>> getByUser(@PathVariable Long userId){
        List<Post> posts = postService.findByUserId(userId);

        List<PostDto> postsDtos = new ArrayList<>();
        for (Post post : posts) {

            postsDtos.add(new PostDto(post));
        }
        return new ResponseEntity<>(postsDtos, HttpStatus.OK);
    }
}
