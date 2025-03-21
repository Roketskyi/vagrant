package com.example.springhelloworld.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GreetController {
    
    @GetMapping("/greet")
    public String greet(Model model) {
        model.addAttribute("message", "Ласкаво просимо до Spring Boot!");
        return "greet";
    }
} 