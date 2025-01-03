package com.onlybuns.isa.service;

import com.onlybuns.isa.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.name}")
    private String exchangeName;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    public void sendMessage(String message) {
        // Pošaljite poruku na RabbitMQ Exchange sa odgovarajućim routing key-jem
        rabbitTemplate.convertAndSend(exchangeName, routingKey, message);
        System.out.println("Message sent: " + message);
    }
}

