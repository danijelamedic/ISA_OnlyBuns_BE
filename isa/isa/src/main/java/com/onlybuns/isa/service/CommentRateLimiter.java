package com.onlybuns.isa.service;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CommentRateLimiter {

    private static final int MAX_COMMENTS_PER_HOUR = 60;

    private final Map<Long, LinkedList<LocalDateTime>> commentTimestamps = new ConcurrentHashMap<>();

    public synchronized boolean allowComment(Long userId) {
        LinkedList<LocalDateTime> timestamps = commentTimestamps.computeIfAbsent(userId, k -> new LinkedList<>());

        LocalDateTime oneHourAgo = LocalDateTime.now().minusHours(1);

        // Ukloni komentare starije od jednog sata
        timestamps.removeIf(timestamp -> timestamp.isBefore(oneHourAgo));

        if (timestamps.size() < MAX_COMMENTS_PER_HOUR) {
            timestamps.add(LocalDateTime.now());
            return true;  // Dozvoljeno postavljanje komentara
        } else {
            return false; // Limit dostignut
        }
    }
}
