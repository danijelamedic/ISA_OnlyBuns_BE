package com.onlybuns.isa.service;

import com.onlybuns.isa.dto.UserDto;
import com.onlybuns.isa.model.Follower;
import com.onlybuns.isa.model.User;
import com.onlybuns.isa.model.UserAction;
import com.onlybuns.isa.repository.FollowerRepository;
import com.onlybuns.isa.repository.UserRepository;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import org.hibernate.PessimisticLockException;
import org.hibernate.exception.LockAcquisitionException;
import org.hibernate.exception.LockTimeoutException;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
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

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private FollowerRepository followerRepository;
    @Autowired
    private UserService userService;

    private final Map<Long, UserAction> userActions = new ConcurrentHashMap<>();
    private final int MAX_ACTIONS_PER_MINUTE = 50;
    @Autowired
    private UserRepository userRepository;

    public List<Follower> findByUserId(Long userId) { return followerRepository.findByUserId(userId); }
    public List<Follower> findByFollowerId(Long followerId) { return followerRepository.findByFollowedUserId(followerId); }

    @RateLimiter(name = "standard", fallbackMethod = "standardFallback")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Follower follow(Follower follower){
        String threadName = Thread.currentThread().getName();
        System.out.println("[" + threadName + "]: TRANSACTION START");
        System.out.println("[" + threadName + "]: Kliknuo sam 'Zaprati'");

        User followedUser = userRepository.findByIdForUpdate(follower.getFollowedUser().getId());
        entityManager.lock(followedUser, LockModeType.PESSIMISTIC_WRITE);
        System.out.println("[" + threadName + "]: Zakljucao sam korisnika");

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        followedUser.setFollowersCount(followedUser.getFollowersCount() + 1);
        userRepository.save(followedUser);

        System.out.println("[" + threadName + "]: Upisao sam vrednost " + followedUser.getFollowersCount() + " u followersCount");
        System.out.println("[" + threadName + "]: TRANSACTION END");

        return followerRepository.save(follower);
    }

    public void unfollow(Follower follower) {
        followerRepository.delete(follower);
        User user = follower.getFollowedUser();
        user.setFollowersCount(user.getFollowersCount() - 1);
        userRepository.save(user);
    }
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
