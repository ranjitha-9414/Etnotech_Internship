package com.example.hospital_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.hospital_system.entity.User;
import com.example.hospital_system.service.UserService;
import com.example.hospital_system.dto.LoginRequest;
import com.example.hospital_system.security.JwtUtil;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    // ✅ Register API (FIXED)
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {

        try {
            User savedUser = userService.registerUser(user);
            return ResponseEntity.ok(savedUser);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ✅ Login API (FIXED)
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        try {
            User user = userService.loginUser(
                    request.getEmail(),
                    request.getPassword()
            );

            String token = jwtUtil.generateToken(user.getEmail());

            return ResponseEntity.ok(token);

        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }
    public String login1(@RequestBody LoginRequest request) {

    User user = userService.loginUser(
            request.getEmail(),
            request.getPassword()
    );

    return jwtUtil.generateToken(user.getEmail());
}
}