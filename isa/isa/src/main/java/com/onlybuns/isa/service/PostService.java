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
    @Autowired
    private LikeService likeService;

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public Post findById(Long id) {
        return postRepository.findById(id).get();
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
}
