package com.onlybuns.isa.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlybuns.isa.config.RabbitMQConfig;
import com.onlybuns.isa.dto.ChatDto;
import com.onlybuns.isa.dto.MessageDto;
import com.onlybuns.isa.model.Chat;
import com.onlybuns.isa.model.Message;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MessageSender {

    @Autowired
    private RabbitMQConfig rabbitMQConfig;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private AmqpAdmin amqpAdmin;

    @Autowired
    private ChatService chatService;

    private final Map<String, String> activeChats = new ConcurrentHashMap<>();  // Cache za chatove

    public void sendMessage(MessageDto message) {
        String chatKey = getChatKey(message);

        ChatDto chat = chatService.findByChatKey(chatKey);

        message.setDateTime(LocalDateTime.now());
        message.setChatId(chat.getId());

        String routingKey = "chat." + chatKey;
        rabbitTemplate.convertAndSend("chat.topic.exchange", routingKey, message);
    }


    public String getChatKey(MessageDto messageDto) {
        /*if (messageDto.getReceiverIds() == null || messageDto.getReceiverIds().isEmpty()) {
            throw new IllegalArgumentException("ReceiverIds must not be null or empty");
        }*/
        System.out.println("ReceiverIds: " + messageDto.getReceiverIds());

        if (messageDto.getReceiverIds().size() == 1) {
            List<Long> ids = new ArrayList<>();
            ids.add(messageDto.getSenderId());
            ids.add(messageDto.getReceiverIds().get(0));
            Collections.sort(ids);
            String userPair = ids.get(0) + "_" + ids.get(1);
            return activeChats.computeIfAbsent(userPair, key -> userPair);
        }
        else {
            return activeChats.computeIfAbsent("group_" + messageDto.getReceiverIds().toString(),
                    key -> "group_" + UUID.randomUUID());
        }
    }

}
