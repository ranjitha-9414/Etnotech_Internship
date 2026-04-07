package com.example.hospital_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity   // ✅ ADD THIS
public class HospitalSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(HospitalSystemApplication.class, args);
    }
}