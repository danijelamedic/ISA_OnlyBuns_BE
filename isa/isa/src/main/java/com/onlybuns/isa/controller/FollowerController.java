package com.onlybuns.isa.controller;

import com.onlybuns.isa.dto.FollowerDto;
import com.onlybuns.isa.dto.UserDto;
import com.onlybuns.isa.model.Follower;
import com.onlybuns.isa.model.User;
import com.onlybuns.isa.service.FollowerService;
import com.onlybuns.isa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/follower")
public class FollowerController {
    @Autowired
    private FollowerService followerService;
    @Autowired
    private UserService userService;

    @GetMapping(value = "/getByUser/{userId}")
    public ResponseEntity<List<FollowerDto>> getByUserId(@PathVariable Long userId) {
        User user = userService.findById(userId);
        List<Follower> followers = followerService.findByUserId(user.getId());
        List<FollowerDto> followerDtos = new ArrayList<>();
        for (Follower follower : followers) {
            followerDtos.add(new FollowerDto(follower));
        }
        return new ResponseEntity<>(followerDtos, HttpStatus.OK);
    }

    @GetMapping(value = "/getByFollowedUser/{followedUserId}")
    public ResponseEntity<List<FollowerDto>> getByFollowedUserId(@PathVariable Long followedUserId) {
        User user = userService.findById(followedUserId);
        List<Follower> followers = followerService.findByFollowerId(user.getId());
        List<FollowerDto> followerDtos = new ArrayList<>();
        for (Follower follower : followers) {
            followerDtos.add(new FollowerDto(follower));
        }
        return new ResponseEntity<>(followerDtos, HttpStatus.OK);
    }

    @PostMapping("/{userId}/{followedUserId}")
    public ResponseEntity<FollowerDto> create(@PathVariable Long userId, @PathVariable Long followedUserId) {
        User user = userService.findById(userId);
        User followedUser = userService.findById(followedUserId);

        if(user == null || followedUser == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Follower follower = new Follower();
        follower.setUser(user);
        follower.setFollowedUser(followedUser);

        follower = followerService.follow(follower);
        return new ResponseEntity<>(new FollowerDto(follower), HttpStatus.CREATED);
    }

    @DeleteMapping("/{userId}/{followedUserId}")
    public ResponseEntity<FollowerDto> delete(@PathVariable Long userId, @PathVariable Long followedUserId) {
        User user = userService.findById(userId);
        User followedUser = userService.findById(followedUserId);

        if(user == null || followedUser == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Follower follower = followerService.findByUserIdAndFollowerId(user.getId(), followedUser.getId());
        followerService.unfollow(follower);
        return new ResponseEntity<>(new FollowerDto(follower), HttpStatus.OK);
    }

    @GetMapping("/getMyFollowers/{userId}")
    public ResponseEntity<List<UserDto>> getMyFollowers(@PathVariable Long userId) {
        User user = userService.findById(userId);
        List<Follower> followers = followerService.findByFollowerId(user.getId());  // dobavlja po followedUser - one koji prate usera
        List<User> myFollowers = new ArrayList<>();
        for (Follower follower : followers) {
            myFollowers.add(follower.getUser());        // dobavlja id onih koji prate usera, tj followedUsera
        }
        List<UserDto> userDtos = new ArrayList<>();
        for (User myFollower : myFollowers) {
            userDtos.add(new UserDto(myFollower));
        }
        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }

    @GetMapping("/getFollowingUsers/{userId}")
    public ResponseEntity<List<UserDto>> getFollowingUsers(@PathVariable Long userId) {
        if(followerService.findFollowedUsers(userId) == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(followerService.findFollowedUsers(userId), HttpStatus.OK);
    }
}
