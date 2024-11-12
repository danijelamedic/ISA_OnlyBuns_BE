package com.onlybuns.isa.service;

import com.onlybuns.isa.dto.UserDto;
import com.onlybuns.isa.dto.UserLoginDto;
import com.onlybuns.isa.dto.UserRegistrationDto;
import com.onlybuns.isa.model.User;
import com.onlybuns.isa.repository.InMemoryUserRepository;
import com.onlybuns.isa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;


@Service
public class UserService implements IUserService{

    @Autowired
    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @Override
    public User create(UserDto user) throws Exception{
        if (user.getId() != null) {
            throw new Exception("Id mora biti null prilikom perzistencije novog entiteta.");
        }
        User savedUser = new User(user);
        return userRepository.save(savedUser);
    }


//    @EventListener(ApplicationReadyEvent.class)
    public void registerUser(UserRegistrationDto userRegistrationDto) {
        User user = new User();
        user.setUsername(userRegistrationDto.getUsername());
        user.setEmail(userRegistrationDto.getEmail());
        user.setName(userRegistrationDto.getName());
        user.setSurname(userRegistrationDto.getSurname());
        user.setAddress(userRegistrationDto.getAddress());
        user.setRole(userRegistrationDto.getRole());

        // Hešovanje lozinke pre čuvanja
        String hashedPassword = passwordEncoder.encode(userRegistrationDto.getPassword());
        user.setPassword(hashedPassword);

        // Generisanje aktivacionog tokena
        String token = UUID.randomUUID().toString();
        //user.setActivationToken(token);

        // Čuvanje korisnika sa tokenom u bazi
        userRepository.save(user);

        // Slanje aktivacionog emaila korisniku
        String activationLink = "http://localhost:8080/api/users/activate?token=" + token;
        emailService.sendEmail(userRegistrationDto.getEmail(), "OnlyBuns: Activate your account",
                "Click the link to activate your account: " + activationLink);
    }


//    public boolean activateUser(String token) {
//        User user = userRepository.findByActivationToken(token).orElse(null);
//
//        if (user != null) {
//            user.setActivated(true);
//            user.setActivationToken(null); // Uklanja token nakon aktivacije
//            userRepository.save(user);
//            return true;
//        } else {
//            return false;
//        }
//    }


    public boolean authenticateUser(UserLoginDto userLoginDto) {
        User user = userRepository.findByUsername(userLoginDto.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Proverite lozinku
        return passwordEncoder.matches(userLoginDto.getPassword(), user.getPassword());
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElse(null);  // Vraća null ako korisnik nije pronađen
    }

//    public User getUserByUsername(String username) {
//        return userRepository.findByUsername(username)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
//    }

}
