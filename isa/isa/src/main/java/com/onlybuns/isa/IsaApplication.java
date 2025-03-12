package com.onlybuns.isa;

import com.onlybuns.isa.service.FollowerService;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
//@EnableRabbit
public class IsaApplication {

	public static void main(String[] args) {
		SpringApplication.run(IsaApplication.class, args);
	}

}