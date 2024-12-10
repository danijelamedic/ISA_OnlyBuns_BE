package com.onlybuns.isa.repository;

import com.onlybuns.isa.model.Like;
import com.onlybuns.isa.model.Post;
import com.onlybuns.isa.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Long> {
    public List<Like> findByPost(Post post);
    public boolean existsByPostAndUser(Post post, User user);
}
