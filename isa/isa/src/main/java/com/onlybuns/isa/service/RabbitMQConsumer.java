package com.onlybuns.isa.service;

import com.onlybuns.isa.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQConsumer {

    private final SimpMessagingTemplate simpMessagingTemplate;

    public RabbitMQConsumer(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @RabbitListener(queues = "chatQueue")
    public void receiveMessage(String message) {
        System.out.println("Received message: " + message);

        // Prosledi WebSocket klijentima
        simpMessagingTemplate.convertAndSend("/socket-publisher", message);
    }

}

