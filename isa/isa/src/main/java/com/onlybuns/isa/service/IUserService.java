package com.onlybuns.isa.service;

import com.onlybuns.isa.dto.UserDto;
import com.onlybuns.isa.model.User;
import com.onlybuns.isa.dto.*;

public interface IUserService {
    User create(UserDto user) throws Exception;

    // Registracija korisnika
    void registerUser(UserRegistrationDto dto) throws Exception;

    // Login korisnika
    boolean authenticateUser(UserLoginDto dto);

    // Aktivacija korisnika putem tokena
    boolean activateUser(String token);

    // Provera dostupnosti korisničkog imena (opcionalno: za AJAX/Bloom filter kasnije)
    boolean existsByUsername(String username);

    // Provera dostupnosti email adrese
    boolean existsByEmail(String email);
}