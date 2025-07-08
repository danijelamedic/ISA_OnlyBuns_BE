package com.onlybuns.isa.mapper;

import com.onlybuns.isa.dto.MessageDto;
import com.onlybuns.isa.dto.UserDto;
import com.onlybuns.isa.model.Message;
import com.onlybuns.isa.model.User;

public class UserMapper {
    public static UserDto toDto(User user) {
        return new UserDto(user);
    }
}
