package com.onlybuns.isa.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class LoadBalancerService {
    private final List<String> instances = List.of(
            "http://localhost:8081",
            "http://localhost:8082"
    );

    private final AtomicInteger index = new AtomicInteger(0);

    public String getNextInstance() {
        int current = Math.abs(index.getAndIncrement());
        return instances.get(current % instances.size());
    }
}
