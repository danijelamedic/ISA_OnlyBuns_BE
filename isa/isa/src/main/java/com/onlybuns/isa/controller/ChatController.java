package com.onlybuns.isa.controller;

import com.onlybuns.isa.dto.ChatDto;
import com.onlybuns.isa.dto.MessageDto;
import com.onlybuns.isa.model.Chat;
import com.onlybuns.isa.model.User;
import com.onlybuns.isa.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/getAll")
    public List<ChatDto> getAllChats() {
        return chatService.findAll();
    }

    @GetMapping("/getChatName/{id}")
    public String getChatName(@PathVariable Long id) {
        return chatService.findById(id).getName();
    }

    @GetMapping("/getReceiverUsername/{id}/{userId}")
    public String getReceiverName(@PathVariable Long id, @PathVariable Long userId) {
        if(chatService.findById(id).getParticipants().size() == 2){
            return chatService.findById(id).getParticipants()
                                            .stream()
                                            .filter(user -> !user.getId().equals(userId))
                                            .findFirst().map(User::getUsername).orElse(null);
        }
        else return "";
    }

    @GetMapping("/getByUser/{id}")
    public List<ChatDto> getByUser(@PathVariable Long id) {
        return chatService.findChatsByParticipantId(id);
    }

    @GetMapping("/getParticipants/{id}")
    public List<Long> getParticipants(@PathVariable Long id) {
        return chatService.findParticipantsIdsByChatId(id);
    }

    @PostMapping("/group")
    public ResponseEntity<ChatDto> createGroupChat(@RequestBody ChatDto chatDto) {
        chatService.createGroupChat(chatDto);
        return ResponseEntity.ok(chatDto);
    }

    @GetMapping("/getKeyById/{id}")
    public String getKeyById(@PathVariable Long id) {
        return chatService.findChatKeyById(id);
    }

    @PutMapping("/deleteUserFromChat/{userId}/{chatId}")
    public ResponseEntity<Void> deleteUserFromChat(@PathVariable Long userId, @PathVariable Long chatId) {
        chatService.removeUserFromParticipants(userId, chatId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/addUserToChat/{userId}/{chatId}")
    public ResponseEntity<Void> addUserToChat(@PathVariable Long userId, @PathVariable Long chatId) {
        chatService.addUserToParticipants(userId, chatId);
        return ResponseEntity.ok().build();
    }


}
