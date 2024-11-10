package com.onlybuns.isa.service;

import com.onlybuns.isa.dto.UserDto;
import com.onlybuns.isa.model.User;
import com.onlybuns.isa.repository.InMemoryUserRepository;
import com.onlybuns.isa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User findById(long id){ return userRepository.findById(id); }

    public User create(User user){
        return userRepository.save(user);
    }
}