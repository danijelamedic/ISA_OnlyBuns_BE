package com.onlybuns.isa.config;

import jakarta.annotation.PostConstruct;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class RabbitMQConfig {
/*
    @Autowired
    private AmqpAdmin amqpAdmin;

    @Autowired
    private Queue chatQueue;

    @PostConstruct
    public void declare() {
        amqpAdmin.declareQueue(chatQueue);
    }*/

    public static final String EXCHANGE_NAME = "chat.topic.exchange";


    @Bean
    public Queue chatQueue() {
        return new Queue("chat.queue", true); // durable=true
    }

    @Bean
    public TopicExchange chatExchange() {
        return new TopicExchange("chat.topic.exchange");
    }

    @Bean
    public Binding binding(Queue chatQueue, TopicExchange chatExchange) {
        return BindingBuilder.bind(chatQueue).to(chatExchange).with("chat.#");
    }

    @Bean
    public AmqpAdmin amqpAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
        //admin.declareQueue(chatQueue());
       // return admin;
    }

    @Bean
    public MessageConverter jacksonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }


    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jacksonMessageConverter());
        return template;
    }
}
