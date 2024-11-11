package com.onlybuns.isa.controller;


import com.onlybuns.isa.dto.PostDto;
import com.onlybuns.isa.model.Post;
import com.onlybuns.isa.service.PostService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Tag(name="Post controller", description = "The post API")
@RestController
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    private PostService postService;


    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<List<PostDto>> getPosts() {
        List<Post> posts = postService.findAll();

        // Ako nema postova, možemo da vratimo 404 Not Found (ili prazan niz)
        if (posts.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        // Mapiranje Post objekata u PostDto objekte
        List<PostDto> postsDtos = posts.stream()
                .map(PostDto::new)
                .collect(Collectors.toList());

//        List<PostDto> postsDtos = new ArrayList<>();
//        for (Post post : posts) {
//
//            postsDtos.add(new PostDto(post));
//        }
        return new ResponseEntity<>(postsDtos, HttpStatus.OK);
    }

//    // Prikaz detalja objave
//    @GetMapping("/{id}")
//    public Post getPostById(@PathVariable Long id) {
//        return postService.getPostById(id);
//    }
}
