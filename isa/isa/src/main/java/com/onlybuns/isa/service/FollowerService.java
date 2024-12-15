package com.onlybuns.isa.service;

import com.onlybuns.isa.dto.UserDto;
import com.onlybuns.isa.model.Follower;
import com.onlybuns.isa.model.User;
import com.onlybuns.isa.repository.FollowerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FollowerService {
    @Autowired
    private FollowerRepository followerRepository;
    @Autowired
    private UserService userService;

    public List<Follower> findByUserId(Long userId) { return followerRepository.findByUserId(userId); }
    public List<Follower> findByFollowerId(Long followerId) { return followerRepository.findByFollowedUserId(followerId); }
    public Follower follow(Follower follower) { return followerRepository.save(follower); }
    public void unfollow(Follower follower) { followerRepository.delete(follower);}
    public Follower findById(long id) { return followerRepository.findById(id); }
    public Follower findByUserIdAndFollowerId(Long userId, Long followerId) { return followerRepository.findByUserIdAndFollowedUserId(userId, followerId); }
    public List<UserDto> findFollowedUsers(Long userId){
        User user = userService.findById(userId);
        List<Follower> followers = findByUserId(user.getId());  // dobavlja po user - oni koje user prati
        List<User> followingUsers = new ArrayList<>();
        for (Follower follower : followers) {
            followingUsers.add(follower.getFollowedUser());     // dobavlja id onih koje user prati, tj followedUsers
        }
        List<UserDto> userDtos = new ArrayList<>();
        for (User followingUser : followingUsers) {
            userDtos.add(new UserDto(followingUser));
        }
        return userDtos;
    }
}
