package com.onlybuns.isa.controller;

import com.onlybuns.isa.dto.PostDto;
import com.onlybuns.isa.dto.UserDto;
import com.onlybuns.isa.model.Post;
import com.onlybuns.isa.model.User;
import com.onlybuns.isa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminController {

    //private final UserService userService;

    @Autowired
    private UserService userService;

    @GetMapping()
    public String adminHome() {
        return "adminHome";
    }

    // Dodajte mape za svaku od stranica
    @GetMapping("/posts")
    public String viewPosts() {
        return "adminPosts";
    }

    @GetMapping("/trends")
    public String viewTrends() {
        return "adminTrends";
    }

    @GetMapping("/analytics")
    public String viewAnalytics() {
        return "adminAnalytics";
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> viewUsers(@RequestParam int page, @RequestParam int size) {
        Page<User> users = userService.findAll(page, size);

        List<UserDto> usersDto = new ArrayList<>();
        for (User user : users) {

            usersDto.add(new UserDto(user));
        }
        return new ResponseEntity<>(usersDto, HttpStatus.OK);
    }


    @GetMapping(value = "/findByName/{name}")
    public ResponseEntity<List<UserDto>> findByName(@PathVariable String name){
        List<User> users = userService.findByName(name);
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            userDtos.add(new UserDto(user));
        }
        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }

    @GetMapping(value = "/findBySurname/{surname}")
    public ResponseEntity<List<UserDto>> findBySurname(@PathVariable String surname){
        List<User> users = userService.findBySurname(surname);
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            userDtos.add(new UserDto(user));
        }
        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }

    @GetMapping(value = "/findByEmail/{email}")
    public ResponseEntity<UserDto> findByEmail(@PathVariable String email){
        User user = userService.findByEmail(email);

        if(user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new UserDto(user), HttpStatus.OK);
    }

    @GetMapping(value = "/findByPostsNumber")
    public ResponseEntity<List<UserDto>> findByPostsNumber(int min, int max){
        List<User> users = userService.findByPostsNumber(min, max);
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            userDtos.add(new UserDto(user));
        }
        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }

    @GetMapping(value = "/sortByEmail")
    public ResponseEntity<List<UserDto>> sortByEmail(){
        List<User> users = userService.sortByEmail();
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            userDtos.add(new UserDto(user));
        }
        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }

    @GetMapping("/users/count")
    public ResponseEntity<Long> getTotalUsersCount() {
        try {
            long count = userService.getTotalUsersCount();
            return ResponseEntity.ok(count);  // Vraća broj korisnika kao Long
        } catch (Exception e) {
            // Vraća grešku u formatu String ako nešto pođe po zlu
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);  // Vraća null telo ako dođe do greške, ili možete ostaviti telo kao Long ako želite
        }
    }


}

