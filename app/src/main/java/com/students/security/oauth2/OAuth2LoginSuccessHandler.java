package com.students.security.oauth2;

import com.students.config.JwtUtils;
import com.students.model.User;
import com.students.service.RefreshTokenService;
import com.students.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtUtils jwtUtils;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                      Authentication authentication) throws IOException {
        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
        
        // Отримуємо або створюємо користувача
        User user = userService.getOrCreateUserFromOAuth2(oauth2User);
        
        // Генеруємо токени
        String accessToken = jwtUtils.generateAccessToken(user);
        String refreshToken = refreshTokenService.createRefreshToken(user).getToken();
        
        // Додаємо токени в куки
        addTokenCookie(response, "access_token", accessToken);
        addTokenCookie(response, "refresh_token", refreshToken);
        
        // Перенаправляємо на головну сторінку
        getRedirectStrategy().sendRedirect(request, response, "/students");
    }

    private void addTokenCookie(HttpServletResponse response, String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(3600); // 1 година
        response.addCookie(cookie);
    }
} 