package com.onlybuns.isa.service;

import com.onlybuns.isa.model.Follower;
import org.hibernate.PessimisticLockException;
import org.hibernate.exception.LockTimeoutException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.serializer.support.SerializationFailedException;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.stereotype.Service;

@Service
public class FollowRetryService {

    @Autowired
    private FollowerService followerService;

    public Follower followWithRetry(Follower follower) {
        int retries = 3;
        long sleepMillis = 500;

        while (true) {
            try {
                return followerService.follow(follower);
            } catch (PessimisticLockException | LockTimeoutException | CannotAcquireLockException |
                     SerializationFailedException e) {
                if (--retries == 0) {
                    throw e;
                }
                try {
                    Thread.sleep(sleepMillis);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException(ie);
                }
                System.out.println("Retrying follow due to lock conflict, attempts left: " + retries);
            }
        }
    }
}