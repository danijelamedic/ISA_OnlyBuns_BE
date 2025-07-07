package com.onlybuns.isa.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlybuns.isa.config.RabbitMQConfig;
import com.onlybuns.isa.dto.ChatDto;
import com.onlybuns.isa.dto.MessageDto;
import com.onlybuns.isa.mapper.MessageMapper;
import com.onlybuns.isa.model.Chat;
import com.onlybuns.isa.model.Message;
import com.onlybuns.isa.repository.MessageRepository;
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

    @Autowired
    private MessageRepository messageRepository;

    private final Map<String, String> activeChats = new ConcurrentHashMap<>();  // Cache za chatove

    public MessageDto sendMessage(MessageDto message) {
        String chatKey = getChatKey(message);

        ChatDto chat = chatService.findByChatKey(chatKey);

        message.setDateTime(LocalDateTime.now());
        message.setChatId(chat.getId());

        String routingKey = "chat." + chatKey;
        rabbitTemplate.convertAndSend("chat.topic.exchange", routingKey, message);
        return message;
    }


    public String getChatKey(MessageDto messageDto) {
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
            if (messageDto.getChatKey() == null || messageDto.getChatKey().isEmpty()) {
                throw new IllegalArgumentException("Missing chatKey for group chat.");
            }
            return messageDto.getChatKey();
        }
    }

    public List<MessageDto> getMessagesByChatId(Long chatId) {
        return messageRepository.findMessagesByChatId(chatId).stream().map(MessageMapper::toDto).toList();
    }

    public List<MessageDto> getLast10Messages(Long chatId) {
        List<Message> messages = messageRepository.findTop10ByChatIdOrderByTimestampDesc(chatId);
        Collections.reverse(messages);
        return messages.stream().map(MessageMapper::toDto).toList();
    }

}
