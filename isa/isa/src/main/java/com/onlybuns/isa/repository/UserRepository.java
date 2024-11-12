package com.onlybuns.isa.repository;

import com.onlybuns.isa.dto.UserDto;
import com.onlybuns.isa.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
   // User create(User user);


    Optional<User> findByUsername(String username);
    //Optional<User> findByActivationToken(String activationToken);
}
