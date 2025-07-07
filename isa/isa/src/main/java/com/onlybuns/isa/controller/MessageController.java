package com.onlybuns.isa.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlybuns.isa.dto.LikeDto;
import com.onlybuns.isa.dto.MessageDto;
import com.onlybuns.isa.model.Message;
import com.onlybuns.isa.service.MessageSender;
import jakarta.servlet.http.HttpServletResponse;
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
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/api/message")
public class MessageController {

    @Autowired
    private MessageSender messageSender;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostMapping("/send")
    public ResponseEntity<MessageDto> sendMessage(@RequestBody MessageDto message) {
        MessageDto savedMessage = messageSender.sendMessage(message);
        return ResponseEntity.ok(savedMessage);
    }

    @GetMapping("/getByChatId/{id}")
    public List<MessageDto> getByChatId(@PathVariable Long id){
        return messageSender.getMessagesByChatId(id);
    }

    @GetMapping("/getLast10Messages/{id}")
    public List<MessageDto> getLast10Messages(@PathVariable Long id){
        return messageSender.getMessagesByChatId(id);
    }

}
