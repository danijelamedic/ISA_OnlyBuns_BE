package com.onlybuns.isa.controller;

import com.onlybuns.isa.dto.CommentDto;
import com.onlybuns.isa.dto.PostDto;
import com.onlybuns.isa.model.Comment;
import com.onlybuns.isa.model.Post;
import com.onlybuns.isa.service.LikeService;
import com.onlybuns.isa.service.LocationService;
import com.onlybuns.isa.service.PostService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    @Autowired
    private LocationService locationService;

    @GetMapping
    public ResponseEntity<List<PostDto>> getPosts(){
        List<Post> posts = postService.findAll();

        List<PostDto> postsDtos = new ArrayList<>();
        for (Post post : posts) {

            postsDtos.add(new PostDto(post));
        }
        return new ResponseEntity<>(postsDtos, HttpStatus.OK);
    }

    @GetMapping(value = "/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Long postId){
        Post post = postService.findById(postId);
        PostDto postDto = new PostDto(post);
        return new ResponseEntity<>(postDto, HttpStatus.OK);
    }

    @GetMapping(value = "/getLikes/{postId}")
    public int getLikesNumber(@PathVariable Long postId){
        Post post = postService.findById(postId);
        return post.getLikes().size();
    }

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

    @PutMapping(consumes = "application/json")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto){
        Post post = postService.findById(postDto.getId());
        if (post == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(postDto.getDescription() != null)
            post.setDescription(postDto.getDescription());
        if(postDto.getImagePath() != null)
            post.setImagePath(postDto.getImagePath());
        post = postService.save(post);
        return new ResponseEntity<>(new PostDto(post), HttpStatus.OK);
    }

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
}
