package com.onlybuns.isa.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlybuns.isa.service.RabbitMQProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;
@RestController
@RequestMapping("/api/chat")
public class MessageController {

    private final RabbitMQProducer rabbitMQProducer;

    public MessageController(RabbitMQProducer rabbitMQProducer) {
        this.rabbitMQProducer = rabbitMQProducer;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestBody String message) {
        rabbitMQProducer.sendMessage(message);
        return ResponseEntity.ok("Message sent to RabbitMQ: " + message);
    }
}
