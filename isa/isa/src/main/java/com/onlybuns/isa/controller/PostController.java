package com.onlybuns.isa.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlybuns.isa.dto.CommentDto;
import com.onlybuns.isa.dto.PostDto;
import com.onlybuns.isa.dto.UpdatePostDto;
import com.onlybuns.isa.dto.UserDto;
import com.onlybuns.isa.model.Comment;
import com.onlybuns.isa.model.Location;
import com.onlybuns.isa.model.Post;
import com.onlybuns.isa.model.User;
import com.onlybuns.isa.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestPart;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.annotation.Timed;

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
    @Autowired
    private FollowerService followerService;
    @Autowired
    private UserService userService;

    private final MeterRegistry meterRegistry;

    public PostController(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @Operation(description = "Get all posts", method = "GET")
    @GetMapping(value = "/getAll")
    public ResponseEntity<List<PostDto>> getPosts(){
        List<Post> posts = postService.findAll();

        List<PostDto> postsDtos = new ArrayList<>();
        for (Post post : posts) {

            postsDtos.add(new PostDto(post));
        }
        return new ResponseEntity<>(postsDtos, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<PostDto>> getOrderedPosts() {
        List<Post> posts = postService.findByOrder();

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
    public ResponseEntity<List<CommentDto>> getComments(@PathVariable Long postId) {
        Post post = postService.findById(postId);
        if (post == null) {
            System.out.println("Post with ID " + postId + " not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        List<Comment> comments = post.getComments();
        if (comments == null) {
            comments = new ArrayList<>(); // fallback zaštita
        }

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

    @PutMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<PostDto> updatePost(@RequestPart("updatePostDto") UpdatePostDto updatePostDto, @RequestPart(value = "imageFile", required = false) MultipartFile imageFile){
        Post post = postService.findById(updatePostDto.getId());
        if (post == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(updatePostDto.getDescription() != null)
            post.setDescription(updatePostDto.getDescription());
        if (imageFile != null && !imageFile.isEmpty()) {
            String imagePath = saveImage(imageFile);
            //String uploadDir = System.getProperty("user.dir") + File.separator + "uploads" + File.separator + "images";
            post.setImagePath(imagePath);
        }
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

    @GetMapping(value="getFollowing/{userId}")
    public ResponseEntity<List<PostDto>> getFollowing(@PathVariable Long userId){
        List<UserDto> following = followerService.findFollowedUsers(userId);
        List<PostDto> postsDtos = new ArrayList<>();
        for (UserDto userDto : following) {
            List<Post> usersPost = postService.findByUserId(userDto.getId());
            for (Post post : usersPost) {
                postsDtos.add(new PostDto(post));
            }
        }
        return new ResponseEntity<>(postsDtos, HttpStatus.OK);
    }

    private String saveImage(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            System.out.println("saveImage: file is null or empty");
            return null;
        }
        try {
            //String uploadDir = "uploads/images";  // relativna putanja
            String uploadDir = System.getProperty("user.dir") + File.separator + "uploads" + File.separator + "images";

            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
                System.out.println("Created directory: " + uploadPath.toAbsolutePath());
            }

            String originalFilename = file.getOriginalFilename();
            String extension = "";

            if (originalFilename != null && originalFilename.contains(".")) {
                extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }

            String newFilename = java.util.UUID.randomUUID().toString() + extension;
            Path filePath = uploadPath.resolve(newFilename);

            file.transferTo(filePath.toFile());

            System.out.println("saveImage: saved file at " + filePath.toString());
            //return uploadDir + "/" + newFilename;
            return "uploads/images/" + newFilename;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }



    @Timed(value = "http_post_create_duration_seconds", histogram = true, description = "Vreme trajanja kreiranja nove objave")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PostDto> createPost(
            @RequestPart("post") String postJson,
            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile) {

        long start = System.nanoTime();

        try {
            ObjectMapper mapper = new ObjectMapper();
            PostDto postDto = mapper.readValue(postJson, PostDto.class);

            Long locationId = postDto.getLocationId();
            Location location = null;
            if (locationId != null) {
                location = locationService.findById(locationId);
                if (location == null) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
                }
            }

            if (imageFile != null && !imageFile.isEmpty()) {
                String imagePath = saveImage(imageFile);
                postDto.setImagePath(imagePath);
            }

            // Kreiraj novi Post entitet
            Post post = new Post();
            post.setDescription(postDto.getDescription());
            post.setLocation(location);
            post.setImagePath(postDto.getImagePath());

            /* //Postavi korisnika (npr. iz SecurityContext)
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.isAuthenticated()) {
                // Pretpostavljam da je principal tip User ili tvoj UserDetails sa getUser()
                Object principal = auth.getPrincipal();
                if (principal instanceof com.onlybuns.isa.model.User) {
                    post.setUser((com.onlybuns.isa.model.User) principal);
                } else {
                    // ili dodatna logika za dobijanje User entiteta na osnovu principal-a
                }
            } else {
                // Nije autentifikovan korisnik, možeš vratiti grešku ili ostaviti post.setUser(null);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
            }*/
            // Hardkodirano postavljanje korisnika sa id = 1

            Long userIdFromFront = postDto.getUserId();
            System.out.println("asfd");
            System.out.println("userIdFromFront je " + userIdFromFront);
            User user = userService.findById(userIdFromFront);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
            post.setUser(user);

            post.setCreationTime(LocalDateTime.now());

            Post savedPost = postService.save(post);

            // Ovde dodaj merenje aktivnosti korisnika:
            meterRegistry.counter("user_activity", "userId", user.getId().toString()).increment();
            //ovo iznad se brise kada daca uradi login, ovo je tren samo za test

            long end = System.nanoTime();
            long durationNanos = end - start;
            meterRegistry.timer("post_create_time_seconds").record(durationNanos, TimeUnit.NANOSECONDS);

            return new ResponseEntity<>(new PostDto(savedPost), HttpStatus.CREATED);

        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
