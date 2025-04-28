package com.students.service;

import com.students.model.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.List;

public interface UserService {
    User createUser(User user);
    User updateUserRoles(Long userId, List<String> roleNames);
    List<User> getAllUsers();
    User getUserById(Long id);
    User getUserByUsername(String username);
    User getOrCreateUserFromOAuth2(OAuth2User oauth2User);
    void sendPasswordResetEmail(String email);
    boolean validatePasswordResetToken(String token);
    void resetPassword(String token, String newPassword);
} 