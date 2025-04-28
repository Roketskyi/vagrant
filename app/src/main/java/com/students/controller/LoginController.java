package com.students.controller;

import com.students.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", "Invalid username or password");
            log.warn("Failed login attempt");
        }
        return "login";
    }

    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String processForgotPassword(@RequestParam("email") String email, RedirectAttributes redirectAttributes) {
        try {
            userService.sendPasswordResetEmail(email);
            redirectAttributes.addFlashAttribute("success", "Password reset instructions have been sent to your email");
            log.info("Password reset requested for email: {}", email);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error processing password reset request");
            log.error("Error processing password reset for email {}: {}", email, e.getMessage());
        }
        return "redirect:/login";
    }

    @GetMapping("/reset-password")
    public String showResetPasswordForm(@RequestParam("token") String token, Model model) {
        if (userService.validatePasswordResetToken(token)) {
            model.addAttribute("token", token);
            return "reset-password";
        }
        return "redirect:/login?error=Invalid or expired password reset token";
    }

    @PostMapping("/reset-password")
    public String processPasswordReset(@RequestParam("token") String token,
                                     @RequestParam("password") String password,
                                     RedirectAttributes redirectAttributes) {
        try {
            userService.resetPassword(token, password);
            redirectAttributes.addFlashAttribute("success", "Your password has been successfully reset");
            log.info("Password successfully reset for token");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error resetting password");
            log.error("Error resetting password for token: {}", e.getMessage());
        }
        return "redirect:/login";
    }
} 