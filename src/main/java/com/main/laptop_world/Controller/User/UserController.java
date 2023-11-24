package com.main.laptop_world.Controller.User;

import com.main.laptop_world.Constant.GlobalFlag;
import com.main.laptop_world.Constant.OTPUser;
import com.main.laptop_world.Entity.DTO.UserDTO;
import com.main.laptop_world.Entity.User;
import com.main.laptop_world.Services.EmailService;
import com.main.laptop_world.Services.GeneralService;
import com.main.laptop_world.Services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {

    private UserService userService;
    private EmailService emailService;
    private GeneralService generalService;
    private final Map<String, Long> lastSentTimeMap = new HashMap<>();

    public UserController(UserService userService, EmailService emailService, GeneralService generalService) {
        this.userService = userService;
        this.emailService = emailService;
        this.generalService = generalService;
    }

    @GetMapping("/login")
    public String getLoginForm(Model model) {
        System.out.println(GlobalFlag.flag);
        if (GlobalFlag.flag) {
            model.addAttribute("status", true);
        } else {
            model.addAttribute("status", false);
        }
        model.addAttribute("title", "Đăng nhập");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("User roles after login: " + authentication.getAuthorities());
        return "users/login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        UserDTO userDTO = new UserDTO();
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
        User existingEmail = userService.findByEmail(user.getEmail());
        if (existingEmail != null) {
            result.rejectValue("email", "error.user",
                    "Email đã được đăng ký!");
            return "/users/register";
        }
        if (user.getPassword().length() < 6) {
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

    @GetMapping("/user/profile")
    public String handleUpdateUser(Model model, Principal principal) {
        Long userId = generalService.usernameHandler(principal);
        User existingUser = userService.findById(userId);
        if (existingUser.getUserDetailEmbeddable() == null) {
            model.addAttribute("errorMessage", "Bạn cần phải nhập đầy đủ thông tin!!!");
        }
        model.addAttribute("user", existingUser);
        model.addAttribute("title", "Trang cá nhân");
        return "users/profile";
    }

    @PostMapping("/user/profile")
    public String updateUserDetail(Principal principal, @ModelAttribute("user") User user, BindingResult result, Model model) {
        Long userId = generalService.usernameHandler(principal);
        User existingUser = userService.findById(userId);
        // Check if the new email is different from the current email
        if (!existingUser.getEmail().equalsIgnoreCase(user.getEmail())) {
            User existingEmailUser = userService.findByEmail(user.getEmail());
            if (existingEmailUser != null) {
                result.rejectValue("email", "error.user", "Email đã được đăng ký!");
                return "/users/profile";
            }
        }
        if (user.getUserDetailEmbeddable() != null) {
            if (existingUser.getUserDetailEmbeddable() == null) {
                if (userService.isPhoneNumberRegistered(user.getUserDetailEmbeddable().getPhoneNumber())) {
                    result.rejectValue("userDetailEmbeddable.phoneNumber", "error.user", "Số điện thoại đã được đăng ký!");
                    return "/users/profile";
                }
                existingUser.setUserDetailEmbeddable(user.getUserDetailEmbeddable());
            } else {
                if (user.getUserDetailEmbeddable().getPhoneNumber() != null &&
                        !existingUser.getUserDetailEmbeddable().getPhoneNumber().equalsIgnoreCase(user.getUserDetailEmbeddable().getPhoneNumber())) {
                    if (userService.isPhoneNumberRegistered(user.getUserDetailEmbeddable().getPhoneNumber())) {
                        result.rejectValue("userDetailEmbeddable.phoneNumber", "error.user", "Số điện thoại đã được đăng ký!");
                        return "/users/profile";
                    }
                    existingUser.getUserDetailEmbeddable().setPhoneNumber(user.getUserDetailEmbeddable().getPhoneNumber());
                }
            }
        }
        userService.updateUser(existingUser);
        model.addAttribute("user", user);
        model.addAttribute("title", "Trang cá nhân");
        return "redirect:/user/profile?success";
    }

    @GetMapping("/send-code")
    public String sendCode(Model model) {
        model.addAttribute("isDisabled", true);
        return "/users/SendCode";
    }

    @PostMapping("/do-sendCode")
    public String doSendCode(@ModelAttribute("email") String email, Model model) {
        User existEmail = userService.findByEmail(email);
        if (existEmail != null && email.equals(existEmail.getEmail())) {
            if (canSendCode(email)) {
                emailService.sendCode(email);
                updateLastSentTime(email);
            } else {
                model.addAttribute("isDisabled", true);
                return "redirect:/send-code?error";
            }
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

    private boolean canSendCode(String email) {
        long currentTime = System.currentTimeMillis();
        long lastSentTime = lastSentTimeMap.getOrDefault(email, 0L);
        long elapsedTime = currentTime - lastSentTime;
        return elapsedTime >= 3 * 60 * 1000; // 3 phút tính bằng milliseconds
//        return elapsedTime >= 60 * 1000;
    }

    private void updateLastSentTime(String email) {
        long currentTime = System.currentTimeMillis();
        lastSentTimeMap.put(email, currentTime);
    }
}
