package com.onlybuns.isa.service;

import com.onlybuns.isa.model.Post;
import com.onlybuns.isa.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    @Autowired
    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    // Metoda za pronalaženje svih objava
    public List<Post> findAll() {
        return postRepository.findAllByOrderByCreationTimeDesc(); // Poziva findAll metodu iz repository-a koja vraća sve objave
    }

    public Post findById(long id) {
        return postRepository.findById(id).orElse(null);
    }

}
