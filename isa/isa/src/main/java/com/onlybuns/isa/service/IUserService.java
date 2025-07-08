package com.onlybuns.isa.service;

import com.onlybuns.isa.dto.UserDto;
import com.onlybuns.isa.model.User;

public interface IUserService {
    User create(UserDto user) throws Exception;
}