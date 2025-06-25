package com.onlybuns.isa.mapper;

import com.onlybuns.isa.dto.ChatDto;
import com.onlybuns.isa.dto.MessageDto;
import com.onlybuns.isa.dto.UserDto;
import com.onlybuns.isa.model.Chat;
import com.onlybuns.isa.model.Message;
import com.onlybuns.isa.model.User;


import java.util.stream.Collectors;

public class MessageMapper {
    public static MessageDto toDto(Message message) {
        return new MessageDto(
                message.getId(),
                message.getChat().getId(),
                message.getSender().getId(),
                message.getReceivers().stream().map(User::getId).collect(Collectors.toList()),
                message.getMessage(),
                message.getTimestamp()
        );
    }
}
