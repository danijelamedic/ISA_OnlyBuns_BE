package com.onlybuns.isa.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlybuns.isa.dto.MessageDto;
import com.onlybuns.isa.model.Message;
import com.onlybuns.isa.service.MessageSender;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;
@RestController
@RequestMapping("/api/chat")
public class MessageController {

    @Autowired
    private MessageSender messageSender;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostMapping("/send")
    public void sendTestMessage(@RequestBody MessageDto message) {
        messageSender.sendMessage(message);
    }

}
