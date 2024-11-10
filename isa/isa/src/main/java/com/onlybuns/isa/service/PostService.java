package com.onlybuns.isa.service;

import com.onlybuns.isa.dto.*;
import com.onlybuns.isa.model.*;
import com.onlybuns.isa.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService implements IPostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Override
    public Post create(PostDto postDto) throws Exception {
        // Proveri korisnika
        User user = userRepository.findById(postDto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Korisnik nije pronađen."));

        // Proveri lokaciju
        Location location = locationRepository.findById(postDto.getLocationId())
                .orElseThrow(() -> new EntityNotFoundException("Lokacija nije pronađena."));

        // Kreiraj i sačuvaj post
        Post post = new Post(postDto, user, location);
        return postRepository.save(post);
    }


    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public Post findById(Long id) {
        return postRepository.findById(id).orElse(null);
    }

}
