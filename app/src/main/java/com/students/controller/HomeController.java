package com.students.controller;

import com.students.dto.ResponseStudentDTO;
import com.students.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@Tag(name = "Home", description = "Home page and navigation")
public class HomeController {
    private final StudentService studentService;

    public HomeController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/")
    @Operation(summary = "Home page", description = "Displays the home page with student statistics")
    public String home(Model model) {
        List<ResponseStudentDTO> students = studentService.getAllStudents();
        model.addAttribute("students", students);
        model.addAttribute("totalStudents", students.size());
        return "index";
    }
} 