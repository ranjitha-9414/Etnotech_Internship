package com.ranjitha.code_execution_engine.service;

import com.ranjitha.code_execution_engine.entity.User;
import com.ranjitha.code_execution_engine.repository.UserRepository;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class AuthService {

    private final UserRepository userRepo;
    private final JavaMailSender mailSender;

    public AuthService(UserRepository userRepo, JavaMailSender mailSender) {
        this.userRepo = userRepo;
        this.mailSender = mailSender;
    }

    public Map<String, String> sendResetLinkByUsername(String username) {

        Map<String, String> res = new HashMap<>();
        System.out.println("Entered username: " + username);
        User user = userRepo.findFirstByUsernameIgnoreCase(username).orElse(null);

        if (user == null) {
            res.put("error", "User not found ❌");
            return res;
        }

        String email = user.getEmail();

        if (email == null || email.isEmpty()) {
            res.put("error", "No email linked to this account ❌");
            return res;
        }

        String token = UUID.randomUUID().toString();

        user.setResetToken(token);
        userRepo.save(user);

        String link = "http://localhost:8080/reset.html?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Password Reset");
        message.setText("Click here to reset password:\n\n" + link);

        mailSender.send(message);

        String masked = maskEmail1(email);

        res.put("maskedEmail", masked);

        return res;
    }
    private String maskEmail1(String email) {

        int at = email.indexOf("@");

        if (at <= 1) return email;

        return email.charAt(0) + "***" + email.substring(at);
    }
    public String resetPassword(String token, String newPass) {

        User user = userRepo.findByResetToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid token"));

        user.setPassword(newPass);
        user.setResetToken(null);

        userRepo.save(user);

        return "Password updated successfully ✅";
    }

    private String maskEmail(String email) {
        int at = email.indexOf("@");
        if (at <= 1) return email;
        return email.charAt(0) + "***" + email.substring(at);
    }
}