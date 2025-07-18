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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
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

    private final Map<String, String> activationTokens = new ConcurrentHashMap<>();


    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    //public User findById(long id){ return userRepository.findById(id); }
    public User findById(long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User create(User user) {
        return userRepository.save(user);
    }


//    @EventListener(ApplicationReadyEvent.class)
//    public void registerUser(UserRegistrationDto userRegistrationDto) {
//        User user = new User();
//        user.setUsername(userRegistrationDto.getUsername());
//        user.setEmail(userRegistrationDto.getEmail());
//        user.setName(userRegistrationDto.getName());
//        user.setSurname(userRegistrationDto.getSurname());
//        user.setAddress(userRegistrationDto.getAddress());
//        user.setRole(userRegistrationDto.getRole());
//
//        // Hešovanje lozinke pre čuvanja
//        String hashedPassword = passwordEncoder.encode(userRegistrationDto.getPassword());
//        user.setPassword(hashedPassword);
//
//        // Generisanje aktivacionog tokena
//        String token = UUID.randomUUID().toString();
//        //user.setActivationToken(token);
//
//        // Čuvanje korisnika sa tokenom u bazi
//        userRepository.save(user);
//
//        // Slanje aktivacionog emaila korisniku
//        String activationLink = "http://localhost:8080/api/users/activate?token=" + token;
//        emailService.sendEmail(userRegistrationDto.getEmail(), "OnlyBuns: Activate your account",
//                "Click the link to activate your account: " + activationLink);
//    }


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


//    public boolean authenticateUser(UserLoginDto userLoginDto) {
//        User user = userRepository.findByUsername(userLoginDto.getUsername())
//                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//
//        // Proverite lozinku
//        return passwordEncoder.matches(userLoginDto.getPassword(), user.getPassword());
//    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElse(null);  // Vraća null ako korisnik nije pronađen
    }

//    public User getUserByUsername(String username) {
//        return userRepository.findByUsername(username)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
//    }

    public Page<User> findAllPaged(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findAll(pageable);
    }

    public List<User> findByName(String name) {
        return userRepository.findByNameContainingIgnoreCase(name);
    }

    public List<User> findBySurname(String surname) {
        return userRepository.findBySurnameContainingIgnoreCase(surname);
    }

    public List<User> findByEmail(String email) {
        return userRepository.findByEmailContainingIgnoreCase(email);
    }

    public User findOneByEmail(String email) {
        List<User> users = findByEmail(email);
        if (users.isEmpty()) return null;
        return users.get(0);
    }



    public List<User> findByPostsNumber(int min, int max) {
        return userRepository.findByPostsNumber(min, max);
    }

    public Page<User> sortByEmail(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findAllByOrderByEmailAsc(pageable);
    }

    public long getTotalUsersCount() {
        return userRepository.count();
    }

    public List<User> havePosts() {
        List<User> users = findAllPaged(0, 1000).getContent();
        List<User> result = new ArrayList<>();
        for (User user : users) {
            if (!postService.findByUserId(user.getId()).isEmpty()) {
                result.add(user);
            }
        }
        return result;
    }

    public List<User> haveComment() {
        List<User> users = findAllPaged(0, 1000).getContent();
        List<User> result = new ArrayList<>();
        for (User user : users) {
            if (!commentService.findByUserId(user.getId()).isEmpty() && postService.findByUserId(user.getId()).isEmpty())
                result.add(user);
        }
        return result;
    }

    public List<User> haveNotAny() {
        List<User> users = findAllPaged(0, 1000).getContent();
        List<User> result = new ArrayList<>();
        for (User user : users) {
            if (commentService.findByUserId(user.getId()).isEmpty() && postService.findByUserId(user.getId()).isEmpty())
                result.add(user);
        }
        return result;
    }

    public List<UserDto> findAll() {
        List<User> users = userRepository.findAll();
        List<UserDto> result = new ArrayList<>();
        UserDto userDto;
        for (User user : users) {
            userDto = new UserDto(user);
            result.add(userDto);
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

    @Scheduled(cron = "0 0 0 L * ?")
    public void deleteNotActivatedAccounts() {
        System.out.println("Deleting not activated accounts");
        List<User> notActivated = userRepository.findByActivatedFalse();
        userRepository.deleteAll(notActivated);
        System.out.println("Deleted " + notActivated.size() + " inactive users");
    }

    public boolean isLastDayOfMonth() {
        LocalDate today = LocalDate.now();
        return today.getDayOfMonth() == today.lengthOfMonth();
    }

    public int getActiveUsersLast24Hours() {
        LocalDateTime since = LocalDateTime.now().minusHours(24);
        return userRepository.countActiveUsersSince(since);
    }


    // REGISTRACIJA
//    @Override
    @Transactional
    public void registerUser(UserRegistrationDto dto) throws Exception {
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new Exception("Username already taken!");
        }
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new Exception("Email already in use!");
        }
        // Možeš ovde simulirati konkurenciju (Thread.sleep ili @Transactional + test)

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setName(dto.getName());
        user.setSurname(dto.getSurname());
        user.setAddress(dto.getAddress());
        user.setRole(dto.getRole());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setActivated(false); // mora aktivacija preko maila

        String token = UUID.randomUUID().toString();
        // user.setActivationToken(token); // ako koristiš ovo polje
        activationTokens.put(token, user.getEmail());

        userRepository.save(user);

        String activationLink = "http://localhost:8080/api/users/activate?token=" + token;
        emailService.sendEmail(dto.getEmail(), "Activate your account",
                "Click this link to activate: " + activationLink);
    }

    // LOGIN
//    @Override
    public boolean authenticateUser(UserLoginDto dto) {
        Optional<User> userOpt = userRepository.findByEmail(dto.getEmail());
        if (userOpt.isEmpty()) return false;
        User user = userOpt.get();
        if (!user.isActivated()) return false;
        return passwordEncoder.matches(dto.getPassword(), user.getPassword());
    }

    // AKTIVACIJA
//    @Override
    public boolean activateUser(String token) {
        // User user = userRepository.findByActivationToken(token).orElse(null);
        // if (user == null) return false;
        // user.setActivated(true);
        // user.setActivationToken(null);
        // userRepository.save(user);
        // return true;

        // Pošto nemaš activationToken, možeš praviti mapu tokenova u memoriji ili kasnije dodati polje u bazu.
//        return false;

        String email = activationTokens.get(token);
        if (email == null) {
            return false; // Token ne postoji ili je istekao
        }
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            return false; // Korisnik ne postoji
        }
        user.setActivated(true);
        userRepository.save(user);
        activationTokens.remove(token); // Ukloni token da se ne može koristiti više puta
        return true;
    }

//    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

//    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
