package com.onlybuns.isa.controller;

import com.onlybuns.isa.dto.LikeDto;
import com.onlybuns.isa.model.Like;
import com.onlybuns.isa.model.Post;
import com.onlybuns.isa.model.User;
import com.onlybuns.isa.service.LikeService;
import com.onlybuns.isa.service.PostService;
import com.onlybuns.isa.service.UserService;
//import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

//@Tag(name="Likes controller", description = "The like API")
@RestController
@RequestMapping("/api/like")
public class LikeController {
    @Autowired
    private LikeService likeService;
    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;

    @GetMapping(value = "/{postId}")
    public ResponseEntity<List<LikeDto>> getByPost(@PathVariable Long postId){
        Post post = postService.findById(postId);
        List<Like> likes = likeService.findByPost(post);

        List<LikeDto> likesDto = new ArrayList<>();
        for (Like like : likes) {
            likesDto.add(new LikeDto(like));
        }
        return new ResponseEntity<>(likesDto, HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<LikeDto> createLike(@RequestBody LikeDto likeDto){
        if(likeDto.getPost() == null || likeDto.getUser() == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Post post = postService.findById(likeDto.getPost().getId());
        User user = userService.findById(likeDto.getUser().getId());

        if(post == null || user == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Like like = new Like();
        like.setUser(user);
        like.setPost(post);

        like = likeService.save(like);
        post.addLike(like);
        postService.save(post);
        return new ResponseEntity<>(new LikeDto(like), HttpStatus.CREATED);
    }
}
