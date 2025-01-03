package com.onlybuns.isa.service;

import com.onlybuns.isa.dto.PostDto;
import com.onlybuns.isa.model.Post;
import com.onlybuns.isa.repository.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private LikeService likeService;

    private final Logger LOG = LoggerFactory.getLogger(PostService.class);

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Cacheable("post")
    public Post findById(Long id) {
        System.out.println("Fetching post from database for ID: " + id);
        return postRepository.findById(id).get();
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
}
