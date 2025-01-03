package com.onlybuns.isa.service;

import com.onlybuns.isa.dto.UserDto;
import com.onlybuns.isa.model.Follower;
import com.onlybuns.isa.model.User;
import com.onlybuns.isa.model.UserAction;
import com.onlybuns.isa.repository.FollowerRepository;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final Logger LOG = LoggerFactory.getLogger(FollowerService.class);


    @Autowired
    private FollowerRepository followerRepository;
    @Autowired
    private UserService userService;

    private final Map<Long, UserAction> userActions = new ConcurrentHashMap<>();
    private final int MAX_ACTIONS_PER_MINUTE = 50;

    public List<Follower> findByUserId(Long userId) { return followerRepository.findByUserId(userId); }
    public List<Follower> findByFollowerId(Long followerId) { return followerRepository.findByFollowedUserId(followerId); }
    @RateLimiter(name = "standard", fallbackMethod = "standardFallback")
    @Transactional(readOnly = false)
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
    // Metoda koja ce se pozvati u slucaju RequestNotPermitted exception-a
    public Follower standardFallback(RequestNotPermitted rnp) {
        LOG.warn("Prevazidjen broj poziva u ogranicenom vremenskom intervalu");
        throw rnp;
    }

    public void testRateLimiter() {
        for (int i = 0; i < 5; i++) {
            try {
                Follower follower = new Follower(); // Primer objekta
                this.follow(follower);
                System.out.println("Request " + (i + 1) + ": SUCCESS");
            } catch (RequestNotPermitted e) {
                System.out.println("Request " + (i + 1) + ": RATE LIMITED");
            }
        }
    }


}
