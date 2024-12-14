package com.onlybuns.isa.service;

import com.onlybuns.isa.model.Follower;
import com.onlybuns.isa.repository.FollowerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FollowerService {
    @Autowired
    private FollowerRepository followerRepository;

    public List<Follower> findByUserId(Long userId) { return followerRepository.findByUserId(userId); }
    public List<Follower> findByFollowerId(Long followerId) { return followerRepository.findByFollowedUserId(followerId); }
    public Follower follow(Follower follower) { return followerRepository.save(follower); }
    public void unfollow(Follower follower) { followerRepository.delete(follower);}
    public Follower findById(long id) { return followerRepository.findById(id); }
    public Follower findByUserIdAndFollowerId(Long userId, Long followerId) { return followerRepository.findByUserIdAndFollowedUserId(userId, followerId); }
}
