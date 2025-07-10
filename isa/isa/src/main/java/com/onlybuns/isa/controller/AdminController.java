package com.onlybuns.isa.controller;

import com.onlybuns.isa.dto.PostDto;
import com.onlybuns.isa.dto.UserDto;
import com.onlybuns.isa.model.Post;
import com.onlybuns.isa.model.User;
import com.onlybuns.isa.service.CommentService;
import com.onlybuns.isa.service.PostService;
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
    @Autowired
    private CommentService commentService;
    @Autowired
    private PostService postService;

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
        Page<User> users = userService.findAllPaged(page, size);

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
    public ResponseEntity<List<UserDto>> findByEmail(@PathVariable String email){
        List<User> users = userService.findByEmail(email);
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            userDtos.add(new UserDto(user));
        }

        return new ResponseEntity<>(userDtos, HttpStatus.OK);
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
    public ResponseEntity<List<UserDto>> sortByEmail(@RequestParam int page, @RequestParam int size){
        Page<User> users = userService.sortByEmail(page, size);
        List<UserDto> usersDto = new ArrayList<>();
        for (User user : users) {
            usersDto.add(new UserDto(user));
        }
        return new ResponseEntity<>(usersDto, HttpStatus.OK);
    }

    @GetMapping("/users/count")
    public ResponseEntity<Long> getTotalUsersCount() {
        try {
            long count = userService.getTotalUsersCount();
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @GetMapping("/getCommentsPerWeek/{week}/{year}")
    public ResponseEntity<Integer> getCommentsPerWeek(@PathVariable int week, @PathVariable int year){
        try{
            int result = commentService.countCommentsPerWeek(week, year);
            return ResponseEntity.ok(result);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getCommentsPerMonth/{month}/{year}")
    public ResponseEntity<Integer> getCommentsPerMonth(@PathVariable int month, @PathVariable int year){
        try{
            int result = commentService.countCommentsPerMonth(month, year);
            return ResponseEntity.ok(result);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getCommentsPerYear/{year}")
    public ResponseEntity<Integer> getCommentsPerYear(@PathVariable int year){
        try{
            int result = commentService.countCommentsPerYear(year);
            return ResponseEntity.ok(result);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/getPostsPerWeek/{week}/{year}")
    public ResponseEntity<Integer> getPostsPerWeek(@PathVariable int week, @PathVariable int year){
        try{
            int result = postService.countPostsPerWeek(week, year);
            return ResponseEntity.ok(result);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getPostsPerMonth/{month}/{year}")
    public ResponseEntity<Integer> getPostsPerMonth(@PathVariable int month, @PathVariable int year){
        try{
            int result = postService.countPostsPerMonth(month, year);
            return ResponseEntity.ok(result);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getPostsPerYear/{year}")
    public ResponseEntity<Integer> getPostsPerYear(@PathVariable int year){
        try{
            int result = postService.countPostsPerYear(year);
            return ResponseEntity.ok(result);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getPostPercent")
    public ResponseEntity<Double> getPostPercent(){
        try{
            double number = userService.havePosts().size();
            double countAll = userService.getTotalUsersCount();
            double result = number / countAll * 100;
            return ResponseEntity.ok(result);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getCommentPercent")
    public ResponseEntity<Double> getCommentPercent(){
        try{
            double number = userService.haveComment().size();
            double countAll = userService.getTotalUsersCount();
            double result = number / countAll * 100;
            return ResponseEntity.ok(result);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getHaveNotAny")
    public ResponseEntity<Double> getHaveNotAny(){
        try{
            double number = userService.haveNotAny().size();
            double countAll = userService.getTotalUsersCount();
            double result = number / countAll * 100;
            return ResponseEntity.ok(result);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
/*
    @GetMapping("getPostUser")
    public ResponseEntity<List<UserDto>> getPostUser(){
        List<UserDto> res = userService.havePosts();
        return ResponseEntity.ok(res);
    }*/
}