package com.onlybuns.isa.dto;

import com.onlybuns.isa.model.User;

import java.util.ArrayList;

public class SortedUsersDto {
    ArrayList<UserDto> following;
    ArrayList<UserDto> followers;
    ArrayList<User> others;
}
