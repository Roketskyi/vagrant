package com.students.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import lombok.extern.slf4j.Slf4j;

import java.security.SecureRandom;
import java.util.Random;

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*";
    private static final int PASSWORD_LENGTH = 10;

    private String generateRandomPassword() {
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();
        
        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            password.append(CHARACTERS.charAt(randomIndex));
        }
        
        return password.toString();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(new AntPathRequestMatcher("/")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/home")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/login")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/css/**")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/js/**")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/favicon.ico")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/error")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/access-denied")).permitAll()
                // Доступ до API тільки для адміністраторів
                .requestMatchers(new AntPathRequestMatcher("/api/students/**")).hasRole("ADMIN")
                // Перегляд списку студентів доступний для всіх автентифікованих користувачів
                .requestMatchers(new AntPathRequestMatcher("/students")).hasAnyRole("ADMIN", "USER")
                // Створення, редагування та видалення тільки для адміністраторів
                .requestMatchers(new AntPathRequestMatcher("/students/edit/**")).hasRole("ADMIN")
                .requestMatchers(new AntPathRequestMatcher("/students/create")).hasRole("ADMIN")
                .requestMatchers(new AntPathRequestMatcher("/students/delete/**")).hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/students")
                .failureUrl("/login?error=true")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/home")
                .permitAll()
            )
            .exceptionHandling(ex -> ex
                .accessDeniedPage("/access-denied")
            )
            .csrf(csrf -> csrf
                .ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**"))
                .ignoringRequestMatchers(new AntPathRequestMatcher("/api/students/**"))
            )
            .headers(headers -> headers
                .frameOptions(frameOptions -> frameOptions.disable())
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        PasswordEncoder encoder = passwordEncoder();
        
        String adminPassword = generateRandomPassword();
        String userPassword = generateRandomPassword();
        
        String encodedAdminPassword = encoder.encode(adminPassword);
        String encodedUserPassword = encoder.encode(userPassword);

        UserDetails admin = User.builder()
            .username("admin")
            .password(encodedAdminPassword)
            .roles("ADMIN")
            .build();

        UserDetails user = User.builder()
            .username("user")
            .password(encodedUserPassword)
            .roles("USER")
            .build();

        log.info("\n==========================================");
        log.info("Generated credentials for login:");
        log.info("Admin credentials:");
        log.info("Username: admin");
        log.info("Password: {}", adminPassword);
        log.info("\nUser credentials:");
        log.info("Username: user");
        log.info("Password: {}", userPassword);
        log.info("==========================================\n");

        return new InMemoryUserDetailsManager(admin, user);
    }
} 