package com.ranjitha.code_execution_engine.controller;

import com.ranjitha.code_execution_engine.entity.User;
import com.ranjitha.code_execution_engine.service.UserService;
import com.ranjitha.code_execution_engine.service.AuthService;
import java.util.Map;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService service;
    private final AuthService authService;
    public AuthController(UserService service, AuthService authService) {
        this.service = service;
        this.authService = authService;
    }

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return service.register(user);
    }
   @PostMapping("/forgot-password")
    public Map<String, String> forgotPassword(@RequestBody Map<String, String> req) {

        String username = req.get("username");

        return authService.sendResetLinkByUsername(username);
    }
    @PostMapping("/reset-password")
public String resetPassword(@RequestBody Map<String, String> req) {

    String token = req.get("token");
    String newPass = req.get("password");

    return authService.resetPassword(token, newPass); // ✅ FIX
}
}