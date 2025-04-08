package com.students.controller;

import com.students.dto.ResponseStudentDTO;
import com.students.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentsController {

    private final StudentService studentService;

    @GetMapping
    public String getStudentsPage(Model model, CsrfToken csrfToken) {
        List<ResponseStudentDTO> students = studentService.getAllStudents();
        model.addAttribute("students", students);
        model.addAttribute("_csrf", csrfToken);
        return "students";
    }
} 