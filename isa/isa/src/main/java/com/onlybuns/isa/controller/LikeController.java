package com.onlybuns.isa.controller;

import com.onlybuns.isa.dto.LikeDto;
import com.onlybuns.isa.model.Like;
import com.onlybuns.isa.model.Post;
import com.onlybuns.isa.model.User;
import com.onlybuns.isa.service.LikeService;
import com.onlybuns.isa.service.PostService;
import com.onlybuns.isa.service.UserService;
//import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

//@Tag(name="Likes controller", description = "The like API")
@RestController
@CrossOrigin(origins = "http://localhost:4200")
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
        List<Like> likes = likeService.findByPostId(postId);

        List<LikeDto> likesDto = new ArrayList<>();
        for (Like like : likes) {
            likesDto.add(new LikeDto(like));
        }
        return new ResponseEntity<>(likesDto, HttpStatus.OK);
    }

    @GetMapping("/countLikes/{id}")
    public ResponseEntity<Integer> countLikes(@PathVariable Long id){
        int result = likeService.findByPostId(id).size();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

   // @PreAuthorize("permitAll()")
    @PostMapping("/likePost/{postId}/{userId}")
    public ResponseEntity<LikeDto> likePost(@PathVariable Long postId, @PathVariable Long userId){
        LikeDto likeDto = likeService.likePost(postId, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(likeDto);
    }
}
