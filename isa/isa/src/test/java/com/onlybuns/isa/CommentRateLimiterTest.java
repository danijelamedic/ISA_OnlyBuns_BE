package com.onlybuns.isa;

import com.onlybuns.isa.service.CommentRateLimiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CommentRateLimiterTest {

    private CommentRateLimiter commentRateLimiter;
    private final Long testUserId = 1L;

    @BeforeEach
    void setUp() {
        commentRateLimiter = new CommentRateLimiter();
    }

    @Test
    void testCommentRateLimiter() {
        int allowedCount = 0;
        int blockedCount = 0;

        for (int i = 1; i <= 70; i++) {
            boolean allowed = commentRateLimiter.allowComment(testUserId);

            if (allowed) {
                allowedCount++;
                System.out.println("Komentar " + i + " dozvoljen.");
            } else {
                blockedCount++;
                System.out.println("Korisnik BLOKIRAN na " + i + ". komentaru (limit: 60 po satu).");
            }
        }

        assertEquals(60, allowedCount, "Treba biti dozvoljeno tačno 60 komentara.");
        assertEquals(10, blockedCount, "Očekuje se 10 blokiranih komentara nakon limita.");
    }
}
