package com.onlybuns.isa.service;

import com.onlybuns.isa.dto.LikeDto;
import com.onlybuns.isa.model.Like;
import com.onlybuns.isa.model.Post;
import com.onlybuns.isa.model.User;
import com.onlybuns.isa.repository.LikeRepository;
import com.onlybuns.isa.repository.PostRepository;
import com.onlybuns.isa.repository.UserRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikeService {
    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;

    public List<Like> findByPostId(Long postId) { return likeRepository.findByPostId(postId); }
    public Like save(Like like) { return likeRepository.save(like); }
    public boolean existsByPostAndUser(Post post, User user) { return likeRepository.existsByPostAndUser(post, user); }
    public LikeDto likePost(Long postId, Long userId){
        Post post = postRepository.findById(postId).orElse(null);
        User user = userRepository.findById(userId).orElse(null);

        Hibernate.initialize(post.getLikes());

        Like like = new Like();
        like.setPost(post);
        like.setUser(user);

        post.addLike(like);
        likeRepository.save(like);
        postRepository.save(post);

        return new LikeDto(like);
    }
}
