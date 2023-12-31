package com.main.laptop_world.Services;

import com.main.laptop_world.Entity.DTO.UserDTO;
import com.main.laptop_world.Entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {
    void saveUser(UserDTO userDto);
    void updateUser(User user);
    User findByUsername(String username);
    User findById(Long id);
    void deleteUser(Long id);
    List<UserDTO> findAllUsers();
    List<User> findAllUser();
    User findByEmail(String email);
    boolean isPhoneNumberRegistered(String phoneNumber);
    User resetPassword(String username,String newPassword);
    void processOAuthPostLogin(String email, String oauth2ClientName);
}

