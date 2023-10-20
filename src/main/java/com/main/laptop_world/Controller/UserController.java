package com.main.laptop_world.Controller;

import com.main.laptop_world.Entity.DTO.UserDTO;
import com.main.laptop_world.Entity.User;
import com.main.laptop_world.Services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String getLoginForm(Model model) {
        model.addAttribute("title", "Đăng nhập");
        return "users/login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        UserDTO userDTO = new UserDTO();
        System.out.println(1);
        model.addAttribute("title", "Đăng ký");
        model.addAttribute("user", userDTO);
        return "users/register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") UserDTO user, BindingResult result, Model model) {
        User existingUser = userService.findByUsername(user.getUsername());
        if (existingUser != null) {
            result.rejectValue("username", "error.user",
                    "Tên tài khoản đã được đăng ký!");
            return "/users/register";
        }
        if(user.getPassword().length() < 6){
            result.rejectValue("password", "error.user", "Mật khẩu phải có từ 8 ký tự trở lên !!!");
            return "/users/register";
        }
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            result.rejectValue("confirmPassword", "error.user", "Mật khẩu không trùng khớp!!!");
            return "/users/register";
        }
        model.addAttribute("user", user);
        userService.saveUser(user);
        return "redirect:/login";
    }
}
