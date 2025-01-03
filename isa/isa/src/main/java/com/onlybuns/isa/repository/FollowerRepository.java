package com.onlybuns.isa.repository;

import com.onlybuns.isa.model.Follower;
import com.onlybuns.isa.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowerRepository extends JpaRepository<Follower, Long> {
    public Follower findById(long id);
    public List<Follower> findByUserId(Long userId);
    public List<Follower> findByFollowedUserId(Long followedUserId);
    public Follower findByUserIdAndFollowedUserId(Long userId, Long followedUserId);
}
