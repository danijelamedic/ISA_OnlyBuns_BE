package com.onlybuns.isa.repository;

import com.onlybuns.isa.model.Follower;
import com.onlybuns.isa.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface FollowerRepository extends JpaRepository<Follower, Long> {
    public Follower findById(long id);
    public List<Follower> findByUserId(Long userId);
    public List<Follower> findByFollowedUserId(Long followedUserId);
    public Follower findByUserIdAndFollowedUserId(Long userId, Long followedUserId);

    @Query("SELECT COUNT(f) FROM Follower f WHERE f.followedUser.id = :userId AND f.followDate > :since")
    int countByFollowedUserIdAndFollowDateAfter(@Param("userId") Long userId, @Param("since") LocalDateTime since);
}
