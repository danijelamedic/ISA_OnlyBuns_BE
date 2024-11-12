package com.onlybuns.isa.controller;

import com.onlybuns.isa.dto.UserDto;
import com.onlybuns.isa.dto.UserRegistrationDto;
import com.onlybuns.isa.model.User;
import com.onlybuns.isa.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

// http://localhost:8080/swagger-ui/index.html

@Tag(name="User controller", description = "The user API")
@RestController
@RequestMapping("/api/users")
@Validated
public class UserController {


    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

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


    @Operation(description = "Register a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User registered successfully",
                    content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "400", description = "Bad Request - Passwords do not match",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content)
    })
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserRegistrationDto userRegistrationDto) {
        // Proveri da li se password poklapa sa confirmPassword
        if (!userRegistrationDto.getPassword().equals(userRegistrationDto.getConfirmPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Password and confirm password do not match.");
        }

        try {
            // Pozivanje metode za registraciju
            userService.registerUser(userRegistrationDto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("User registered successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Registration failed: " + e.getMessage());
        }
    }
    @Operation(description = "Activate user account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User activated successfully",
                    content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "400", description = "Invalid or expired token",
                    content = @Content)
    })
    @GetMapping("/activate")
    public ResponseEntity<String> activateUser(@RequestParam("token") String token) {
        //boolean isActivated = userService.activateUser(token);

        boolean isActivated = true;
        if (isActivated) {
            return ResponseEntity.ok("User activated successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid or expired token.");
        }
    }

//    // Prikaz profila korisnika po username-u
//    @GetMapping("/{username}")
//    public User getUserProfile(@PathVariable String username) {
//        return userService.getUserByUsername(username);
//    }

}
