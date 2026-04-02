package com.example.timeManagementApi.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.timeManagementApi.entity.PasswordResetToken;
import com.example.timeManagementApi.entity.User;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByToken(String token);
    void deleteByUser(User user); // Good for cleaning up old tokens
    void deleteByExpiryDateBefore(LocalDateTime now);
}