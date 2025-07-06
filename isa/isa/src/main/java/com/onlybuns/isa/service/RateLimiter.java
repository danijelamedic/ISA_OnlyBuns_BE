package com.onlybuns.isa.service;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Component
public class RateLimiter {

    private final Map<Long, Deque<LocalDateTime>> userCommentTimestamps = new HashMap<>();

    private static final int MAX_PER_MINUTE = 5;
    private static final int MAX_PER_HOUR = 60;

    public synchronized boolean allowRequest(Long userId) {
        LocalDateTime now = LocalDateTime.now();
        Deque<LocalDateTime> timestamps = userCommentTimestamps.getOrDefault(userId, new ArrayDeque<>());

        // Očisti sve komentare starije od 1h
        while (!timestamps.isEmpty() && ChronoUnit.MINUTES.between(timestamps.peekFirst(), now) >= 60) {
            timestamps.pollFirst();
        }

        // Provera 60 po satu
        if (timestamps.size() >= MAX_PER_HOUR) {
            return false;
        }

        // Provera 5 po minuti
        long commentsLastMinute = timestamps.stream()
                .filter(t -> ChronoUnit.SECONDS.between(t, now) < 60)
                .count();

        if (commentsLastMinute >= MAX_PER_MINUTE) {
            return false;
        }

        timestamps.addLast(now);
        userCommentTimestamps.put(userId, timestamps);
        return true;
    }
}