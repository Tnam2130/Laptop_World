package com.main.laptop_world.Services;

import com.main.laptop_world.Entity.DTO.UserDTO;
import com.main.laptop_world.Entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    void saveUser(UserDTO userDto);
    User findByUsername(String username);
    List<UserDTO> findAllUsers();
}

