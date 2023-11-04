package com.main.laptop_world.Controller.User;

import com.main.laptop_world.Entity.DTO.UserDTO;
import com.main.laptop_world.Entity.User;
import com.main.laptop_world.Services.EmailService;
import com.main.laptop_world.Services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class UserController {

    private UserService userService;
    private EmailService emailService;
    public UserController(UserService userService, EmailService emailService){
        this.userService=userService;
        this.emailService=emailService;
    }

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
        User existingEmail=userService.findByEmail(user.getEmail());
        if(existingEmail!=null){
            result.rejectValue("email", "error.user",
                    "Email đã được đăng ký!");
            return "/users/register";
        }
        if(user.getPassword().length() < 6){
            result.rejectValue("password", "error.user", "Mật khẩu phải có từ 6 ký tự trở lên !!!");
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
    @GetMapping ("/user/profile")
    public String handleUpdateUser(Model model, Principal principal){
        String username=principal.getName();
        User existingUser=userService.findByUsername(username);
        if(existingUser == null){
            System.out.println("User not found!");
            return "error";
        }
        if(existingUser.getUserDetailEmbeddable() == null){
            model.addAttribute("errorMessage", "Bạn cần phải nhập đầy đủ thông tin!!!");
        }
        model.addAttribute("user", existingUser);
        model.addAttribute("title","Trang cá nhân");
        return "users/profile";
    }
    @PostMapping("/user/profile")
    public String updateUserDetail(Principal principal, @ModelAttribute("user") User user){
        String username=principal.getName();
        User existingUser=userService.findByUsername(username);
        if(existingUser == null){
            System.out.println("User not found!");
            return "error";
        }
        existingUser.setUserDetailEmbeddable(user.getUserDetailEmbeddable());
        userService.updateUser(existingUser);
        return "redirect:/user/profile";
    }

    @GetMapping("/send-code")
    public String sendCode() {
        return "/users/SendCode";
    }

    @PostMapping("/do-sendCode")
    public String doSendCode(@ModelAttribute("email") String email) {
        User existEmail = userService.findByEmail(email);

        if (existEmail != null) {
            emailService.sendCode(email);
            return "redirect:/check-code?email=" + email;
        } else {
            return "redirect:/send-code?error";
        }
    }

    @GetMapping("/check-code")
    public String checkCode(Model model, @RequestParam(name = "email", defaultValue = "") String email) {
        model.addAttribute("email", email);
        return "users/CheckCode";
    }

    @PostMapping("/do-checkCode")
    public String doCheckCode(@RequestParam("email") String email, @ModelAttribute("code") String code) {
        User users = emailService.getUserByCode(code);
        if (users != null) {
            return "redirect:/resetPassword?email=" + email;
        } else {
            return "redirect:/check-code?error";
        }
    }

    @GetMapping("/resetPassword")
    public String resetPassword(Model model, @RequestParam(name = "email") String email) {
        System.out.println(email);
        model.addAttribute("email", email);
        return "users/ResetPassword";
    }

    @PostMapping("/do-resetPassword")
    public String doResetPassword(@ModelAttribute("email") String email, @ModelAttribute("password1") String newPassword, @ModelAttribute("password2") String repeatPassword) {
        if (repeatPassword.equals(newPassword)) {
            userService.resetPassword(email, repeatPassword);
            System.out.println("ok");
            return "redirect:/login";
        } else {
            System.out.println("no ok");
            return "redirect:/resetPassword?email=" + email + "?error";
        }
    }
}
