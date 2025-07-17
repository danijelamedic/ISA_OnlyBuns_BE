package com.onlybuns.isa.service;

import com.onlybuns.isa.model.User;
import com.onlybuns.isa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserStatisticsService statisticsService;

    @Autowired
    private JavaMailSender mailSender;

    @Scheduled(cron = "0 49 21 * * *") // svakog dana u 21:29
    public void notifyInactiveUsers() {
        LocalDateTime oneWeekAgo = LocalDateTime.now().minusDays(7);
        List<User> inactiveUsers = userRepository.findByLastLoginTimeBefore(oneWeekAgo);

        System.out.println("Broj neaktivnih korisnika: " + inactiveUsers.size());

        for (User user : inactiveUsers) {
            String stats = statisticsService.generateStatisticsForUser(user);
            sendSimpleMessage(user.getEmail(), "We miss you! \uD83E\uDD79", stats);
        }
    }

    private void sendSimpleMessage(String to, String subject, String text) {
        System.out.println("Šaljem mejl korisniku: " + to);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("slike.rimini2024@gmail.com"); // ili iz configa
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }
}
