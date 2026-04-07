package com.ranjitha.code_execution_engine.repository;

import com.ranjitha.code_execution_engine.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findFirstByUsernameIgnoreCase(String username);
    Optional<User> findByEmail(String email);
Optional<User> findByResetToken(String token);

    
}