package com.onlybuns.isa.repository;

import com.onlybuns.isa.dto.UserDto;
import com.onlybuns.isa.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    public User findById(long id);
}
