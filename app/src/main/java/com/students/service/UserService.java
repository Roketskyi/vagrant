package com.students.service;

import com.students.model.User;
import java.util.List;

public interface UserService {
    User createUser(User user);
    User updateUserRoles(Long userId, List<String> roleNames);
    List<User> getAllUsers();
    User getUserById(Long id);
    void sendPasswordResetEmail(String email);
    boolean validatePasswordResetToken(String token);
    void resetPassword(String token, String newPassword);
} 