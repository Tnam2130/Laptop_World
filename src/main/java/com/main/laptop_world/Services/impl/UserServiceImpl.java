package com.main.laptop_world.Services.impl;

import com.main.laptop_world.Entity.DTO.UserDTO;
import com.main.laptop_world.Entity.Role;
import com.main.laptop_world.Entity.User;
import com.main.laptop_world.Repository.RoleRepository;
import com.main.laptop_world.Repository.UserRepository;
import com.main.laptop_world.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private UserDTO convertEntityToDto(User user) {
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
        user.setEmail(UserDTO.getEmail());
        //encrypt the password once we integrate spring security
        //user.setPassword(UserDTO.getPassword());
        user.setPassword(passwordEncoder.encode(UserDTO.getPassword()));
        user.setUsername(UserDTO.getUsername());
        Role role = roleRepository.findByName("USER");
        if (role == null) {
            role = checkRoleExist();
        }
        user.setRoles(List.of(role));
        userRepository.save(user);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public void updateUser(User user) {
        User existingUser= userRepository.findById(user.getId()).orElse(null);
        if (existingUser != null) {
            // Kiểm tra xem trường mật khẩu đã được cung cấp trong biểu mẫu cập nhật hay không
            if (user.getPassword() == null || user.getPassword().equals(existingUser.getPassword())) {
                // Nếu mật khẩu không được cung cấp, giữ nguyên mật khẩu hiện tại
                user.setPassword(passwordEncoder.encode(existingUser.getPassword()));
            }
            user.setUsername(user.getUsername());
            user.setEmail(user.getEmail());
            user.setActive(user.isActive());
            userRepository.save(user);
        }

    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteUser(Long id) {
        this.userRepository.deleteById(id);
    }

    @Override
    public List<UserDTO> findAllUsers() {
        List<User> User = userRepository.findAll();
        return User.stream().map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> findAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User resetPassword(String email, String newPassword) {
        return Optional.ofNullable(findByEmail(email))
                .map(users -> {
                    users.setPassword(passwordEncoder.encode(newPassword));
                    return userRepository.save(users);
                }).orElse(null);
    }

    @Override
    public void processOAuthPostLogin(String username) {
//        User existUser = userRepository.findByUsername(username);
//        if (existUser == null){
//            User newUser=new User();
//            newUser.setUsername(username);
//            newUser.setUserDetailEmbeddable(new UserDetailEmbeddable(null, null, null, username));
//            newUser.setAuthProvider(AuthenticationProvider.GOOGLE);
//            userRepository.save(newUser);

    }

}
