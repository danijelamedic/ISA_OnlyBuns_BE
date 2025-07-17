package com.onlybuns.isa.service;

import com.onlybuns.isa.model.User;
import org.springframework.stereotype.Service;
import com.onlybuns.isa.service.UserService;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class UserStatisticsService {

    private final UserService userService;
    private final FollowerService followerService;
    private final PostService postService;
    private final LikeService likeService;
    private final EmailService emailService; // servis za slanje emailova
    private final CommentService commentService;

    public UserStatisticsService(UserService userService, FollowerService followerService,
                                 PostService postService, LikeService likeService, EmailService emailService, CommentService commentService) {
        this.userService = userService;
        this.followerService = followerService;
        this.postService = postService;
        this.likeService = likeService;
        this.emailService = emailService;
        this.commentService = commentService;
    }

    public String generateStatisticsForUser(User user) {
        if (user == null) {
            return ""; //moram da vratim string
        }

        Long userId = user.getId();

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime lastLogin = user.getLastLoginTime();

        if (lastLogin == null || ChronoUnit.DAYS.between(lastLogin, now) > 7) {
            int newFollowers = followerService.countNewFollowers(userId, lastLogin);
            int newLikes = likeService.countNewLikes(userId, lastLogin);
            int newComments = commentService.countNewComments(userId, lastLogin);
            // int newPosts = postService.countNewPosts(userId, lastLogin);

            String emailBody = String.format("""
            Hello %s,
            Here's what happened while you were away:
            - New followers: %d
            - Likes: %d
            - New comments: %d
            See you soon!
            """, user.getUsername(), newFollowers, newLikes, newComments);

            //emailService.sendEmail(user.getEmail(), "We miss you \uD83E\uDD79", emailBody);
            return emailBody;
        } else {
            // Ako je korisnik bio aktivan u poslednjih 7 dana, nema statistike za slanje
            return ""; // ili neki drugi string, npr. "User active recently"
        }
    }
}