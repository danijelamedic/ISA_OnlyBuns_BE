package com.onlybuns.isa.mapper;

import com.onlybuns.isa.dto.ChatDto;
import com.onlybuns.isa.dto.MessageDto;
import com.onlybuns.isa.dto.UserDto;
import com.onlybuns.isa.model.Chat;
import com.onlybuns.isa.model.Message;
import com.onlybuns.isa.model.User;
import com.onlybuns.isa.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;
import java.util.stream.Collectors;


public class MessageMapper {
    public static MessageDto toDto(Message message) {
        return new MessageDto(
                message.getId(),
                message.getChat().getId(),
                message.getSender().getId(),
                message.getMessage(),
                message.getTimestamp()
        );
    }
}
