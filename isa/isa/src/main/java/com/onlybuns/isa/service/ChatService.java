package com.onlybuns.isa.service;

import com.onlybuns.isa.dto.ChatDto;
import com.onlybuns.isa.mapper.ChatMapper;
import com.onlybuns.isa.model.Chat;
import com.onlybuns.isa.model.Type;
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
                    chat.setType(Type.PRIVATE);
                    chat.setParticipants(fetchParticipantsFromKey(chatKey));
                    return chatRepository.save(chat);
                });

        return ChatMapper.toDto(chatt);
    }

    private List<User> fetchParticipantsFromKey(String chatKey) {
        if (!chatKey.startsWith("group_")) {
            String[] ids = chatKey.split("_");
            List<Long> userIds = new ArrayList<>();
            for (String id : ids) {
                userIds.add(Long.parseLong(id));
            }
            return userRepository.findAllById(userIds);
        } else return null;
    }

    public Chat findById(Long id) {
        return chatRepository.findById(id).orElse(null);
    }

    public Chat findByName(String name) {
        return chatRepository.findByName(name);
    }

    public List<ChatDto> findAll() {
        return chatRepository.findAll().stream().map(ChatMapper::toDto).toList();
    }

    public void save(Chat chat) {
        chatRepository.save(chat);
    }

    public List<ChatDto> findChatsByParticipantId(Long id){
        return chatRepository.findChatsByParticipantId(id).stream().map(ChatMapper::toDto).toList();
    }

    public List<Long> findParticipantsIdsByChatId(Long chatId) {
        return chatRepository.findParticipantsIdsByChatId(chatId);
    }

    public void createGroupChat(ChatDto chatDto){
        Chat chat = new Chat();
        chat.setName(chatDto.getName());

        String chatKey = "group_" + UUID.randomUUID().toString();
        chat.setChatKey(chatKey);

        chat.setType(chatDto.getType());

        User admin = userRepository.getById(chatDto.getAdminId());
        chat.setAdmin(admin);

        List<User> participants = userRepository.findAllById(chatDto.getParticipantIds());
        chat.setParticipants(participants);

        chatRepository.save(chat);
    }

    public String findChatKeyById(Long chatId){
        return chatRepository.findChatKeyById(chatId);
    }

    public void removeUserFromParticipants(Long userId, Long chatId){
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new RuntimeException("Chat not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        chat.getParticipants().remove(user);
        chatRepository.save(chat);
    }
}
