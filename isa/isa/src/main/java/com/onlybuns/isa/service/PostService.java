package com.onlybuns.isa.service;

import com.onlybuns.isa.dto.PostDto;
import com.onlybuns.isa.model.Post;
import com.onlybuns.isa.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public Post findOne(Long id) {
        return postRepository.findById(id).orElseGet(null);
    }

    public Post save(Post post) {
        return postRepository.save(post);
    }

    public void deleteById(long id) {
        postRepository.deleteById(id);
    }
}