package com.onlybuns.isa.controller;
import com.onlybuns.isa.service.RabbitMQProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
public class MessageController {

    @Autowired
    private RabbitMQProducer rabbitMQProducer;

    @PostMapping("/message")
    public String sendMessage(@RequestParam String message) {
        rabbitMQProducer.sendMessage(message);
        return "Message sent: " + message;
    }
}

