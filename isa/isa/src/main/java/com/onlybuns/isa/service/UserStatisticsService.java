package com.onlybuns.isa.service;

import com.onlybuns.isa.model.User;
import org.springframework.stereotype.Service;
import com.onlybuns.isa.service.UserService;

@Service
public class UserStatisticsService {

    private final UserService userService;

    public UserStatisticsService(UserService userService) {
        this.userService = userService;
    }

    public String generateStatisticsForUser(User user) {
        int brojLajkova = 2;
        int brojPratilaca = 3;

            return String.format("""
            Hello %s,
            Here's what happened while you were away:
            - New followers: %d
            - Likes: %d
            See you soon!
            """, user.getUsername(), brojPratilaca, brojLajkova);
    }
}
