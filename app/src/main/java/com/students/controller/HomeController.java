package com.students.controller;

import com.students.dto.ResponseStudentDTO;
import com.students.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final StudentService studentService;

    @GetMapping("/home")
    public String home(Model model) {
        List<ResponseStudentDTO> students = studentService.getAllStudents();
        model.addAttribute("students", students);
        // For demonstration, we'll use a hardcoded admin user
        model.addAttribute("username", "Admin");
        model.addAttribute("isAdmin", true);
        return "home";
    }
} 