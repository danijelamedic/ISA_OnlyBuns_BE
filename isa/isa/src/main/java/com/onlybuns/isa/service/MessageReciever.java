package com.onlybuns.isa.service;

import ch.qos.logback.classic.Logger;
import com.onlybuns.isa.dto.MessageDto;
import com.onlybuns.isa.model.Chat;
import com.onlybuns.isa.model.Message;
import com.onlybuns.isa.model.User;
import com.onlybuns.isa.repository.ChatRepository;
import com.onlybuns.isa.repository.MessageRepository;
import com.onlybuns.isa.repository.UserRepository;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MessageReciever {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ChatRepository chatRepository;

    private static final Logger log = (Logger) LoggerFactory.getLogger(MessageReciever.class);

    @RabbitListener(queues = "chat.queue")
    public void recieveMessage(@Payload MessageDto messageDto){
        Message message = new Message();

        Long chatId = messageDto.getChatId();
        Chat chat = chatRepository.findChatById(chatId);
        message.setChat(chat);

        Optional<User> sender = userRepository.findById(messageDto.getSenderId());
        if(sender.isEmpty()){
            log.warn("Sender with id {} not found, skipping message", messageDto.getSenderId());
            return;
        }
        else{
            User user = sender.get();
            message.setSender(user);
        }

        message.setMessage(messageDto.getMessage());
        message.setTimestamp(messageDto.getDateTime() != null ? messageDto.getDateTime() : LocalDateTime.now());

        messageRepository.save(message);

//        if(isUserPartOfChat(chatId, message)){
        messagingTemplate.convertAndSend("/topic/chat." + chat.getChatKey(), messageDto);
//        }
    }

}
