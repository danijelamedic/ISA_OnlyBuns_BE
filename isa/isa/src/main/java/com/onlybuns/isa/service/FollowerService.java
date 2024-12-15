package com.onlybuns.isa.service;

import com.onlybuns.isa.dto.UserDto;
import com.onlybuns.isa.model.Follower;
import com.onlybuns.isa.model.User;
import com.onlybuns.isa.model.UserAction;
import com.onlybuns.isa.repository.FollowerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class FollowerService {
    @Autowired
    private FollowerRepository followerRepository;
    @Autowired
    private UserService userService;

    private final Map<Long, UserAction> userActions = new ConcurrentHashMap<>();
    private final int MAX_ACTIONS_PER_MINUTE = 50;

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

    public boolean canFollow(long userId) {
        Instant now = Instant.now();
        UserAction userAction = userActions.getOrDefault(userId, new UserAction(now, 0));

        if (now.minusSeconds(60).isAfter(userAction.getLastActionTime())) {

            userAction.setLastActionTime(now);
            userAction.setActionCount(0);
        }

        if (userAction.getActionCount() < MAX_ACTIONS_PER_MINUTE) {
            userAction.incrementActionCount();
            userActions.put(userId, userAction);
            return true;
        }

        return false;
    }

}
