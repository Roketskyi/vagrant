package com.students.controller;

import com.students.dto.ResponseStudentDTO;
import com.students.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {

    private final StudentService studentService;

    public HomeController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public String getHomePage() {
        return "home";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }
} 