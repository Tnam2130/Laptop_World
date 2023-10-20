package com.main.laptop_world.Services.impl;

import com.main.laptop_world.Entity.DTO.UserDTO;
import com.main.laptop_world.Entity.Role;
import com.main.laptop_world.Entity.User;
import com.main.laptop_world.Repository.RoleRepository;
import com.main.laptop_world.Repository.UserRepository;
import com.main.laptop_world.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsererviceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private UserDTO convertEntityToDto(User user){
        UserDTO UserDTO = new UserDTO();
        UserDTO.setUsername(user.getUsername());
        System.out.println(user.getUsername());
        return UserDTO;
    }

    private Role checkRoleExist() {
        Role role = new Role();
        role.setName("USER");
        return roleRepository.save(role);
    }
    @Override
    public void saveUser(UserDTO UserDTO) {
        User user = new User();
        user.setUsername(UserDTO.getUsername());
        System.out.println(UserDTO.getUsername());
        //encrypt the password once we integrate spring security
        //user.setPassword(UserDTO.getPassword());
        user.setPassword(passwordEncoder.encode(UserDTO.getPassword()));
        user.setUsername(UserDTO.getUsername());
        Role role = roleRepository.findByName("USER");
        if(role == null){
            role = checkRoleExist();
        }
        user.setRoles(List.of(role));
        userRepository.save(user);
    }


    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<UserDTO> findAllUsers() {
        List<User> User = userRepository.findAll();
        return User.stream().map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }
    
}
