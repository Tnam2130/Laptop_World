package com.main.asm.service;

import com.main.asm.entity.UserDto;
import com.main.asm.entity.Users;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);
    Users findByEmail(String email);
    List<UserDto> findAllUsers();
}

