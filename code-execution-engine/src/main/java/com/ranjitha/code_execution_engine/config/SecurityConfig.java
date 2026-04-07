package com.ranjitha.code_execution_engine.config;

import java.beans.Customizer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.ranjitha.code_execution_engine.repository.UserRepository;

@Configuration
public class SecurityConfig {

    // 🔐 LOAD USER FROM DATABASE
    @Bean
    public UserDetailsService userDetailsService(UserRepository repo) {
        return username -> repo.findFirstByUsernameIgnoreCase(username)
                .map(user -> org.springframework.security.core.userdetails.User
                        .withUsername(user.getUsername())
                        .password(user.getPassword()) // ✅ encoded password
                        .roles("USER")
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    // 🔒 SECURITY CONFIG
    /**
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/",
                    "/index.html",
                    "/login.html",
                    "/register.html",
                    "/style.css",
                    "/app.js",
                    "/register.js",
                    "/effects.js",
                    "/api/auth/register"
                ).permitAll()
                .anyRequest().permitAll()
            )
            
            .formLogin(form -> form.disable());

        return http.build();
    }

    // 🔐 PASSWORD ENCODER
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}