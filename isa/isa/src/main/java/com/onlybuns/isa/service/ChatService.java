package com.onlybuns.isa.service;

import com.onlybuns.isa.dto.ChatDto;
import com.onlybuns.isa.model.Chat;
import com.onlybuns.isa.model.User;
import com.onlybuns.isa.repository.ChatRepository;
import com.onlybuns.isa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private UserRepository userRepository;

    public ChatDto findOrCreatePrivateChat(Long senderId, Long receiverId) {
        List<Long> ids = new ArrayList<>(List.of(senderId, receiverId));
        Collections.sort(ids); // da key bude uvek istog reda
        String chatKey = ids.get(0) + "_" + ids.get(1);

        return findByChatKey(chatKey);
    }

    public ChatDto findByChatKey(String chatKey) {
        Chat chatt =  chatRepository.findByChatKey(chatKey)
                .orElseGet(() -> {
                    Chat chat = new Chat();
                    chat.setChatKey(chatKey);
                    chat.setType("private");
                    chat.setParticipants(fetchParticipantsFromKey(chatKey));
                    return chatRepository.save(chat);
                });

        return new ChatDto(chatt.getId(), chatt.getChatKey(), chatt.getName(), chatt.getType());
    }

    private List<User> fetchParticipantsFromKey(String chatKey) {
        String[] ids = chatKey.split("_");
        List<Long> userIds = new ArrayList<>();
        for (String id : ids) {
            userIds.add(Long.parseLong(id));
        }
        return userRepository.findAllById(userIds);
    }

    public Chat findById(Long id) {
        return chatRepository.findById(id).orElse(null);
    }

    public Chat findByName(String name) {
        return chatRepository.findByName(name);
    }

    public List<Chat> findAll() {
        return chatRepository.findAll();
    }

    public void save(Chat chat) {
        chatRepository.save(chat);
    }
}
