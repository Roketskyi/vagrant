package com.students.service;

import com.students.config.JwtUtils;
import com.students.model.RefreshToken;
import com.students.model.User;
import com.students.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtils jwtUtils;

    @Transactional
    public RefreshToken createRefreshToken(User user) {
        // Спочатку видаляємо існуючий токен для цього користувача
        refreshTokenRepository.findByUser(user)
            .ifPresent(refreshTokenRepository::delete);
        
        // Створюємо новий токен
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(Instant.now().plusMillis(jwtUtils.getRefreshTokenExpiration()));
        
        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken verifyExpiration(String token) {
        return refreshTokenRepository.findByToken(token)
                .map(this::verifyExpiration)
                .orElseThrow(() -> new RuntimeException("Refresh token not found!"));
    }

    private RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException("Refresh token was expired. Please make a new login request");
        }
        return token;
    }

    @Transactional
    public void deleteByUser(User user) {
        refreshTokenRepository.deleteByUser(user);
    }
} 