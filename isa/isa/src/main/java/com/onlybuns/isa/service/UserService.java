package com.onlybuns.isa.service;

import com.onlybuns.isa.dto.UserDto;
import com.onlybuns.isa.model.User;
import com.onlybuns.isa.repository.InMemoryUserRepository;
import com.onlybuns.isa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class UserService implements IUserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public User create(UserDto user) throws Exception{
        if (user.getId() != null) {
            throw new Exception("Id mora biti null prilikom perzistencije novog entiteta.");
        }
        User savedUser = new User(user);
        return userRepository.save(savedUser);
    }
}
