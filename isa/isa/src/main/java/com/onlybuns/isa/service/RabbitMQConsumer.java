package com.onlybuns.isa.service;

import com.onlybuns.isa.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQConsumer {

    @RabbitListener(queues = "chatQueue")
    public void receiveMessage(String message) {
        System.out.println("Received message: " + message);
    }
}

