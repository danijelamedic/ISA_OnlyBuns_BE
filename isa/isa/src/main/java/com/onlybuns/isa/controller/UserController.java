package com.onlybuns.isa.controller;

import com.onlybuns.isa.dto.UserDto;
import com.onlybuns.isa.model.User;
import com.onlybuns.isa.repository.UserRepository;
import com.onlybuns.isa.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// http://localhost:8080/swagger-ui/index.html

@Tag(name="User controller", description = "The user API")
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping(consumes = "application/json")
    public ResponseEntity<UserDto> create(@RequestBody UserDto userDto) {
        User user = new User(userDto);

        user = userService.create(user);
        return new ResponseEntity<>(new UserDto(user), HttpStatus.CREATED);
    }
}
