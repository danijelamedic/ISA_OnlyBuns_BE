package com.onlybuns.isa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // CSRF je onemogućen radi jednostavnijeg testiranja
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/posts/**").permitAll()  // Dozvoljeno svima
                        .requestMatchers("/api/auth/**").permitAll()  // Omogućeno za registraciju i prijavu
                        .anyRequest().authenticated()  // Sve ostalo zahteva prijavu
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll()
                )
                .logout(logout -> logout.permitAll());

        return http.build();
    }
}
