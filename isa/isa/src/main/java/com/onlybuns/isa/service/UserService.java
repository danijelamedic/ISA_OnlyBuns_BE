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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Autowired
    private PostService postService;
    @Autowired
    private CommentService commentService;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    //public User findById(long id){ return userRepository.findById(id); }
    public User findById(long id) {
        Optional<User> optionalUser = Optional.ofNullable(userRepository.findById(id));
        return optionalUser.orElse(null);
    }

    public User create(User user){
        return userRepository.save(user);
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

    public Page<User> findAll(int page, int size)
    {
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findAll(pageable);
    }

    public List<User> findByName(String name){
        return userRepository.findByNameContainingIgnoreCase(name);
    }
    public List<User> findBySurname(String surname){
        return userRepository.findBySurnameContainingIgnoreCase(surname);
    }
    public User findByEmail(String email){
        return userRepository.findByEmailContainingIgnoreCase(email);
    }
    public List<User> findByPostsNumber(int min, int max){
        return userRepository.findByPostsNumber(min, max);
    }
    public List<User> sortByEmail(){
        return userRepository.findAllByOrderByEmailAsc();
    }

    public long getTotalUsersCount(){
        return userRepository.count();
    }

    public List<User> havePosts(){
        List<User> users = findAll(0, 1000).getContent();
        List<User> result = new ArrayList<>();
        for(User user : users){
            if(!postService.findByUserId(user.getId()).isEmpty()){
                result.add(user);
            }
        }
        return result;
    }

    public List<User> haveComment(){
        List<User> users = findAll(0, 1000).getContent();
        List<User> result = new ArrayList<>();
        for(User user : users){
            if(!commentService.findByUserId(user.getId()).isEmpty() && postService.findByUserId(user.getId()).isEmpty())
                result.add(user);
        }
        return result;
    }

    public List<User> haveNotAny(){
        List<User> users = findAll(0, 1000).getContent();
        List<User> result = new ArrayList<>();
        for(User user : users){
            if(commentService.findByUserId(user.getId()).isEmpty() && postService.findByUserId(user.getId()).isEmpty())
                result.add(user);
        }
        return result;
    }

    public Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("User not authenticated");
        }

        String username = authentication.getName();
        User user = findByUsername(username);

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        return user.getId();
    }

    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("User not authenticated");
        }

        return authentication.getName();
    }

}
