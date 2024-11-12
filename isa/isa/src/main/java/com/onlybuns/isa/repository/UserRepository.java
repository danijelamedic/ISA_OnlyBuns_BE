package com.onlybuns.isa.repository;

import com.onlybuns.isa.dto.UserDto;
import com.onlybuns.isa.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
    public User findById(long id);

    @Query("select u from users u join fetch u.posts p where u.id = ?1")
    public User findOneWithPosts(long userId);
}