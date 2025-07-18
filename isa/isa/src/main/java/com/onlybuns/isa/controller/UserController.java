package com.onlybuns.isa.controller;

import com.onlybuns.isa.dto.UserDto;
import com.onlybuns.isa.dto.UserLoginDto;
import com.onlybuns.isa.dto.UserRegistrationDto;
import com.onlybuns.isa.model.User;
import com.onlybuns.isa.repository.UserRepository;
import com.onlybuns.isa.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// http://localhost:8080/swagger-ui/index.html

@Tag(name="User controller", description = "The user API")
@RestController
@RequestMapping("/api/users")
@Validated
public class UserController {

    @Autowired
    private UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }
    @Autowired
    private UserRepository userRepository;

    @PostMapping(consumes = "application/json")
    public ResponseEntity<UserDto> create(@RequestBody UserDto userDto) {
        User user = new User(userDto);

        user = userService.create(user);
        return new ResponseEntity<>(new UserDto(user), HttpStatus.CREATED);
    }

    @GetMapping(value="/{id}")
    public ResponseEntity<UserDto> get(@PathVariable Long id) {
        User user = userService.findById(id);
        return new ResponseEntity<>(new UserDto(user), HttpStatus.OK);
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
        boolean isActivated = userService.activateUser(token);

        //boolean isActivated = true;
        if (isActivated) {
            return ResponseEntity.ok("User activated successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid or expired token.");
        }
    }

    @Operation(description = "Login user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful"),
            @ApiResponse(responseCode = "401", description = "Invalid username or password"),
            @ApiResponse(responseCode = "500", description = "Login failed")
    })
//    @PostMapping("/login")
//    public ResponseEntity<String> loginUser(@RequestBody UserLoginDto userLoginDto) {
//        User user = userService.findOneByEmail(userLoginDto.getEmail());
//
//        if (user != null && passwordEncoder.matches(userLoginDto.getPassword(), user.getPassword())) {
//            return ResponseEntity.ok("Login successful!");
//        } else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
//        }
//    }
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserLoginDto userLoginDto) {
        User user = userService.findOneByEmail(userLoginDto.getEmail());

        if (user != null && passwordEncoder.matches(userLoginDto.getPassword(), user.getPassword())) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Login successful!");
            response.put("userId", user.getId());
            response.put("email", user.getEmail());
            response.put("username", user.getUsername());
            // Dodaj još polja po potrebi
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid email or password");
        }
    }


    @GetMapping("/getAll")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

//    // Prikaz profila korisnika po username-u
//    @GetMapping("/{username}")
//    public User getUserProfile(@PathVariable String username) {
//        return userService.getUserByUsername(username);
//    }

}
