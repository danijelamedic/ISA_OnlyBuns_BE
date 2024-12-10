package com.onlybuns.isa.service;

import com.onlybuns.isa.model.Like;
import com.onlybuns.isa.model.Post;
import com.onlybuns.isa.model.User;
import com.onlybuns.isa.repository.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikeService {
    @Autowired
    private LikeRepository likeRepository;

    public List<Like> findByPost(Post post) { return likeRepository.findByPost(post); }
    public Like save(Like like) { return likeRepository.save(like); }
    public boolean existsByPostAndUser(Post post, User user) { return likeRepository.existsByPostAndUser(post, user); }
}
