package com.onlybuns.isa.repository;

import com.onlybuns.isa.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
