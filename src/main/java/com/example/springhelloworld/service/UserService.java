package com.example.springhelloworld.service;

import com.example.springhelloworld.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final List<User> users = new ArrayList<>();

    public UserService() {
        // Додаємо тестові дані
        users.add(new User(1L, "Іван Петренко", "ivan@example.com"));
        users.add(new User(2L, "Марія Коваленко", "maria@example.com"));
        users.add(new User(3L, "Олександр Сидоренко", "oleksandr@example.com"));
    }

    public List<User> getAllUsers() {
        return users;
    }
} 