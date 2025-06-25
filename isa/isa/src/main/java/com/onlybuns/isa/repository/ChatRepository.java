package com.onlybuns.isa.repository;

import com.onlybuns.isa.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    public Chat findByName(String name);
    public Chat findChatById(Long id);
    public Optional<Chat> findByChatKey(String chatKey);
    @Query("SELECT c FROM Chat c JOIN c.participants p WHERE p.id = :userId")
    List<Chat> findChatsByParticipantId(@Param("userId") Long userId);
    @Query("SELECT u.id FROM Chat c JOIN c.participants u WHERE c.id = :chatId")
    List<Long> findParticipantsIdsByChatId(Long chatId);
}
