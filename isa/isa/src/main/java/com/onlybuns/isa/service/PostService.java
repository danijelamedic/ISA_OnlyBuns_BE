package com.onlybuns.isa.service;

import com.onlybuns.isa.dto.PostDto;
import com.onlybuns.isa.model.Like;
import com.onlybuns.isa.model.Post;
import com.onlybuns.isa.model.User;
import com.onlybuns.isa.repository.PostRepository;
import com.onlybuns.isa.repository.UserRepository;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.onlybuns.isa.model.Location;
import com.onlybuns.isa.service.LocationService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;  // dodaj ovo
    @Autowired
    private LikeService likeService;
    @Autowired
    private LocationService locationService;  // ubaci servis za lokaciju

    private final Logger LOG = LoggerFactory.getLogger(PostService.class);

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Cacheable("post")
    public Post findById(Long id) {
        System.out.println("Fetching post from database for ID: " + id);
        return postRepository.findById(id).orElse(null);
    }

    // Metoda za pronalaženje svih objava
    public List<Post> findByOrder() {
        return postRepository.findAllByOrderByCreationTimeDesc(); // Poziva findAll metodu iz repository-a koja vraća sve objave
    }

    public Post save(Post post) {
        return postRepository.save(post);
    }

    public void deleteById(long id) {
        postRepository.deleteById(id);
    }

    public List<Post> findByUserId(Long userId) {
        return postRepository.findByUserId(userId);
    }

    public int countPostsPerMonth(int month, int year){ return postRepository.countPerMonth(month,year);}
    public int countPostsPerWeek(int week, int year){ return postRepository.countPerWeek(week,year);}
    public int countPostsPerYear(int year){ return postRepository.countPerYear(year);}

    public String saveImage(MultipartFile file) {
        try {
            // folder gde cuvas slike, npr "uploads/"
            Path uploadPath = Paths.get("uploads");
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            // Jedinstveno ime fajla, možeš dodati timestamp ili UUID
            String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = uploadPath.resolve(filename);
            Files.copy(file.getInputStream(), filePath);

            // Vrati putanju kao string, npr "uploads/12345_image.png"
            return filePath.toString();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to store file");
        }
    }

    public Post create(PostDto postDto) {
        Post post = new Post();

        // Popunjavanje osnovnih polja iz DTO-a
        post.setDescription(postDto.getDescription());
        post.setImagePath(postDto.getImagePath()); // postavi putanju do slike

        // Postavljanje korisnika
        User user = userRepository.findById(postDto.getUserId()).orElse(null);
        if (user == null) {
            throw new IllegalArgumentException("User with id " + postDto.getUserId() + " not found.");
        }
        post.setUser(user);

        // Postavljanje lokacije ako postoji
        if (postDto.getLocationId() != null) {
            Location location = locationService.findById(postDto.getLocationId());
            if (location == null) {
                throw new IllegalArgumentException("Location with id " + postDto.getLocationId() + " not found.");
            }
            post.setLocation(location);
        }

        // Postavljanje vremena kreiranja
        post.setCreationTime(LocalDateTime.now());


        user.setLastActivityTime(LocalDateTime.now());
        userRepository.save(user);
        // Čuvanje posta u bazi
        return postRepository.save(post);
    }
}
