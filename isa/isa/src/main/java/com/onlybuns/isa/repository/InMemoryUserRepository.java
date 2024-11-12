package com.onlybuns.isa.repository;

import com.onlybuns.isa.model.User;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryUserRepository {
    /*private static AtomicLong counter = new AtomicLong();

    private final ConcurrentMap<Long, User> users = new ConcurrentHashMap<Long, User>();

    @Override
    public User create(User user){
        Long id = user.getId();

        if(id == null){
            id = counter.incrementAndGet();
            user.setId(id);
        }

        this.users.put(id, user);
        return user;
    }*/
}