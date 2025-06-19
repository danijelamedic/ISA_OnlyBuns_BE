package com.onlybuns.isa.repository;

import com.onlybuns.isa.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    public Chat findByName(String name);
    public Chat findChatById(Long id);
    public Optional<Chat> findByChatKey(String chatKey);
}
