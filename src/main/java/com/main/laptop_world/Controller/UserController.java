package com.main.laptop_world.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
    @GetMapping("/login")
    public String getLoginForm(Model model) {
        model.addAttribute("title", "Đăng nhập");
        return "users/login";
    }
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
//        UserDto user=new UserDto();
        model.addAttribute("title", "Đăng ký");
//        model.addAttribute("user", user);
        return "users/register";
    }
}
