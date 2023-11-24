package com.main.laptop_world.Services;

import com.main.laptop_world.Constant.OTPUser;
import com.main.laptop_world.Entity.User;
import com.main.laptop_world.Constant.EmailType;

public interface EmailService {
    void sendEmail(String to, String code, EmailType emailType);
    void sendCode(String email);
    String generateCode();
    User getUserByCode(String code);
}
