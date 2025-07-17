package com.onlybuns.isa.service;

import com.onlybuns.isa.repository.UserRepository;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ActiveUserMetrics {

    private final MeterRegistry meterRegistry;
    private final UserRepository userRepository;

    public ActiveUserMetrics(MeterRegistry meterRegistry, UserRepository userRepository) {
        this.meterRegistry = meterRegistry;
        this.userRepository = userRepository;

        Gauge.builder("active_users_last_24h", this, ActiveUserMetrics::countActiveUsers)
                .description("Broj korisnika aktivnih u poslednja 24h")
                .register(meterRegistry);
    }

    public double countActiveUsers() {
        LocalDateTime since = LocalDateTime.now().minusHours(24);
        return userRepository.countActiveUsersSince(since);
    }
}
