package com.main.laptop_world.Services.impl;

import com.main.laptop_world.Entity.User;
import com.main.laptop_world.Services.EmailService;
import com.main.laptop_world.Services.UserService;
import com.main.laptop_world.Constant.EmailType;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class EmailServiceImpl implements EmailService {
    private static final String EMAIL_WELCOME_SUBJECT = "WELCOME TO LAPTOP WORLD";

    private static final String EMAIL_SEND_CODE = "LAPTOP WORLD - YOUR CODE FORGET PASSWORD";

    private Map<String, User> codeUserMap = new HashMap<>();
    UserService userService;
    private final JavaMailSender mailSender;

    public EmailServiceImpl(UserService userService, JavaMailSender mailSender){
        this.userService=userService;
        this.mailSender = mailSender;

    }

    @Override
    public void sendEmail(String to,String code, EmailType emailType) {
        SimpleMailMessage message = new SimpleMailMessage();
        String content = null;
        String subject = null;
        switch (emailType){
            case WELCOME_TO_WEBSITE :
                subject = EMAIL_WELCOME_SUBJECT;
                content = "Love You!!!";
                break;
            case EMAIL_SEND_CODE:
                subject = EMAIL_SEND_CODE;
                content = "Your Code Here : " + code;
                break;
            default:
                subject = "LAPTOP WORLD ONLINE";
                content = "Email Wrong";
        }
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);

        mailSender.send(message);


    }
    @Override
    public String generateCode() {
        return String.valueOf((int) (Math.random() * 900000 + 100000));
    }



    @Override
    public void sendCode(String email) {
        Optional.ofNullable(userService.findByEmail(email))
                .map(users -> {
                    String code = generateCode();
                    sendEmail(email, code, EmailType.EMAIL_SEND_CODE);
                    return codeUserMap.put(code, users);
                });
    }


    @Override
    public User getUserByCode(String code){
        return codeUserMap.get(code);
    }
}
