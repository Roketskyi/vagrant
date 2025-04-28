package com.students.controller;

import com.students.config.JwtUtils;
import com.students.dto.AuthRequest;
import com.students.dto.AuthResponse;
import com.students.dto.RefreshTokenRequest;
import com.students.model.RefreshToken;
import com.students.model.User;
import com.students.service.RefreshTokenService;
import com.students.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtils jwtUtils;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        User user = (User) authentication.getPrincipal();
        String accessToken = jwtUtils.generateAccessToken(user);
        String refreshToken = jwtUtils.generateRefreshToken(user);

        return ResponseEntity.ok(new AuthResponse(accessToken, refreshToken));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        RefreshToken refreshToken = refreshTokenService.verifyExpiration(request.getRefreshToken());
        User user = refreshToken.getUser();

        String accessToken = jwtUtils.generateAccessToken(user);
        String newRefreshToken = jwtUtils.generateRefreshToken(user);

        return ResponseEntity.ok(new AuthResponse(accessToken, newRefreshToken));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        refreshTokenService.deleteByUser(user);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/oauth2/success")
    public ResponseEntity<AuthResponse> oauth2Success(OAuth2AuthenticationToken authentication) {
        OAuth2User oauth2User = authentication.getPrincipal();
        String email = oauth2User.getAttribute("email");
        
        User user = userService.getOrCreateUserFromOAuth2(oauth2User);
        String accessToken = jwtUtils.generateAccessToken(user);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);
        
        return ResponseEntity.ok(new AuthResponse(accessToken, refreshToken.getToken()));
    }
} 