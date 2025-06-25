package com.onlybuns.isa.mapper;

import com.onlybuns.isa.dto.ChatDto;
import com.onlybuns.isa.model.Chat;

public class ChatMapper {
    public static ChatDto toDto(Chat chat) {
        return new ChatDto(
                chat.getId(),
                chat.getChatKey(),
                chat.getName(),
                chat.getType()
        );
    }
}
