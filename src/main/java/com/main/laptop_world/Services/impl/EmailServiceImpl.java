package com.main.laptop_world.Services.impl;

import com.main.laptop_world.Constant.OTPUser;
import com.main.laptop_world.Entity.User;
import com.main.laptop_world.Services.EmailService;
import com.main.laptop_world.Services.UserService;
import com.main.laptop_world.Constant.EmailType;
import jakarta.annotation.PostConstruct;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class EmailServiceImpl implements EmailService {
    private static final String EMAIL_WELCOME_SUBJECT = "WELCOME TO LAPTOP WORLD";
    private static final String EMAIL_SEND_CODE = "LAPTOP WORLD - YOUR CODE FORGET PASSWORD";
    private final Map<String, User> codeUserMap = new HashMap<>();
    private final Map<String, OTPUser> codeExpiryMap = new HashMap<>();
    UserService userService;
    private final JavaMailSender mailSender;
    public EmailServiceImpl(UserService userService, JavaMailSender mailSender){
        this.userService=userService;
        this.mailSender = mailSender;

    }

    @Override
    public void sendEmail(String to,String code, EmailType emailType) {
        MimeMessage message= mailSender.createMimeMessage();
        MimeMessageHelper helper=new MimeMessageHelper(message);
        String content = null;
        String subject = null;
        content = switch (emailType) {
            case WELCOME_TO_WEBSITE -> {
                subject = EMAIL_WELCOME_SUBJECT;
                yield "Love You!!!";
            }
            case EMAIL_SEND_CODE -> {
                subject = EMAIL_SEND_CODE;
                yield "Mã OTP của bạn: <strong>"+ code+"</strong>";
            }
            default -> {
                subject = "LAPTOP WORLD ONLINE";
                yield "Email Wrong";
            }
        };
        try {
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content);
            message.setContent(content,"text/html; charset=\"utf-8\"");
            mailSender.send(message);

        }catch (Exception e){
            e.printStackTrace();
        }

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
                    LocalDateTime expirationTime=LocalDateTime.now().plusMinutes(3);
                    codeUserMap.put(code, users);
                    codeExpiryMap.put(code, new OTPUser(users, expirationTime));

                    sendEmail(email, code, EmailType.EMAIL_SEND_CODE);
                    return code;
                }).orElse(null);
    }

    @Override
    public User getUserByCode(String code){
        return codeUserMap.get(code);
    }

    @PostConstruct//Sẽ được gọi khi service được khởi tạo
    public void startScheduler() {
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

        // Xóa OTP hết hạn mỗi 1 phút (có thể điều chỉnh theo nhu cầu)
        scheduler.scheduleAtFixedRate(this::removeExpiredCodes, 0, 1, TimeUnit.MINUTES);
    }
    private void removeExpiredCodes() {
        LocalDateTime now = LocalDateTime.now();
        codeExpiryMap.entrySet().removeIf(entry -> {
            if (entry.getValue().expirationTime().isBefore(now)) {
                codeUserMap.remove(entry.getKey()); // Xóa mã code từ cả hai Map
                return true; // Xóa nếu hết hạn
            }
            return false;
        });
    }
}
