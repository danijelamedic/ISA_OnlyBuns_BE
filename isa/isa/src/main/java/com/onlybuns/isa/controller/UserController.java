package com.onlybuns.isa.controller;

import com.onlybuns.isa.dto.PostDto;
import com.onlybuns.isa.dto.UserDto;
import com.onlybuns.isa.model.Post;
import com.onlybuns.isa.model.User;
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
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

// http://localhost:8080/swagger-ui/index.html

@Tag(name="User controller", description = "The user API")
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(description = "Create new greeting", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)) }),
            @ApiResponse(responseCode = "409", description = "Not possible to create new greeting when given id is not null or empty",
                    content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createUser(@RequestBody UserDto user) {
        User savedUser = null;
        try {
            savedUser = userService.create(user);
            return new ResponseEntity<User>(savedUser, HttpStatus.CREATED);
        } catch(Exception e){
            return new ResponseEntity<User>(savedUser, HttpStatus.CONFLICT);
        }
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers() {
        List<User> users = userService.findAll();

        List<UserDto> usersDto = new ArrayList<>();
        for (User user : users) {

            usersDto.add(new UserDto(user));
        }
        return new ResponseEntity<>(usersDto, HttpStatus.OK);
    }
}
