package com.students.service.impl;

import com.students.model.Role;
import com.students.model.User;
import com.students.repository.RoleRepository;
import com.students.repository.UserRepository;
import com.students.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;

    @PersistenceContext
    private EntityManager entityManager;

    @Value("${app.password-reset.token.expiration}")
    private long resetTokenExpirationMs;

    @Value("${app.password-reset.email.from}")
    private String fromEmail;

    @Override
    @Transactional
    public User createUser(User user) {
        log.info("Creating new user: {}", user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseGet(() -> {
                    Role newRole = new Role("ROLE_USER");
                    return roleRepository.save(newRole);
                });
        user.getRoles().add(userRole);
        user.setEnabled(true);
        
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User updateUserRoles(Long userId, List<String> roleNames) {
        log.info("Updating roles for user with id: {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        Set<Role> roles = roleNames.stream()
                .map(roleName -> roleRepository.findByName(roleName)
                        .orElseThrow(() -> new RuntimeException("Role " + roleName + " not found")))
                .collect(Collectors.toSet());
        
        user.setRoles(roles);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        log.info("Getting all users");
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        log.info("Getting user by id: {}", id);
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    @Transactional
    public void sendPasswordResetEmail(String email) {
        log.info("Processing password reset request for email: {}", email);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        String token = UUID.randomUUID().toString();
        LocalDateTime expiryDate = LocalDateTime.now().plusSeconds(resetTokenExpirationMs / 1000);

        // Save the token in the database
        entityManager.createNativeQuery(
            "INSERT INTO password_reset_tokens (token, user_id, expiry_date) VALUES (?, ?, ?)")
            .setParameter(1, token)
            .setParameter(2, user.getId())
            .setParameter(3, expiryDate)
            .executeUpdate();

        // Send email
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(user.getEmail());
        message.setSubject("Password Reset Request");
        message.setText("To reset your password, click the link below:\n\n" +
                "http://localhost:8080/reset-password?token=" + token + "\n\n" +
                "This link will expire in 1 hour.");

        mailSender.send(message);
        log.info("Password reset email sent to: {}", email);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean validatePasswordResetToken(String token) {
        log.info("Validating password reset token");
        List<Object[]> results = entityManager.createNativeQuery(
            "SELECT user_id, expiry_date FROM password_reset_tokens WHERE token = ?")
            .setParameter(1, token)
            .getResultList();

        if (results.isEmpty()) {
            log.warn("Token not found: {}", token);
            return false;
        }

        Object[] result = results.get(0);
        LocalDateTime expiryDate = ((java.sql.Timestamp) result[1]).toLocalDateTime();

        return LocalDateTime.now().isBefore(expiryDate);
    }

    @Override
    @Transactional
    public void resetPassword(String token, String newPassword) {
        log.info("Resetting password using token");
        List<Object[]> results = entityManager.createNativeQuery(
            "SELECT user_id, expiry_date FROM password_reset_tokens WHERE token = ?")
            .setParameter(1, token)
            .getResultList();

        if (results.isEmpty()) {
            throw new RuntimeException("Invalid token");
        }

        Object[] result = results.get(0);
        Long userId = ((Number) result[0]).longValue();
        LocalDateTime expiryDate = ((java.sql.Timestamp) result[1]).toLocalDateTime();

        if (LocalDateTime.now().isAfter(expiryDate)) {
            throw new RuntimeException("Token has expired");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        // Delete the used token
        entityManager.createNativeQuery("DELETE FROM password_reset_tokens WHERE token = ?")
            .setParameter(1, token)
            .executeUpdate();

        log.info("Password successfully reset for user: {}", user.getUsername());
    }
} 