package com.onlybuns.isa.controller;

import com.onlybuns.isa.model.Chat;
import com.onlybuns.isa.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chats")
@CrossOrigin
public class ChatController {

    @Autowired
    private ChatService chatService;

    @GetMapping("/{id}")
    public Chat getChatById(@PathVariable Long id) {
        return chatService.findById(id);
    }

    @GetMapping
    public List<Chat> getAllChats() {
        return chatService.findAll();
    }
}
