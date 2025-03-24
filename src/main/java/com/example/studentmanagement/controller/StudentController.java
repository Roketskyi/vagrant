package com.example.studentmanagement.controller;

import com.example.studentmanagement.model.Student;
import com.example.studentmanagement.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.validation.Valid;
import java.util.Collections;

@Controller
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public String listStudents(Model model) {
        model.addAttribute("students", studentService.findAll());
        model.addAttribute("student", new Student());
        return "students";
    }

    @GetMapping("/search")
    public String searchStudent(@RequestParam(required = false) Long id, Model model, RedirectAttributes redirectAttributes) {
        if (id == null) {
            redirectAttributes.addFlashAttribute("error", "Будь ласка, введіть ID студента");
            return "redirect:/students";
        }
        
        return studentService.findById(id)
                .map(student -> {
                    model.addAttribute("student", new Student());
                    model.addAttribute("students", Collections.singletonList(student));
                    return "students";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("message", "Студента з ID " + id + " не знайдено");
                    return "redirect:/students";
                });
    }

    @GetMapping("/{id}")
    public String showStudent(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return studentService.findById(id)
                .map(student -> {
                    model.addAttribute("student", student);
                    model.addAttribute("students", studentService.findAll());
                    return "students";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("message", "Студента з ID " + id + " не знайдено");
                    return "redirect:/students";
                });
    }

    @PostMapping
    public String saveStudent(@Valid @ModelAttribute Student student,
                            BindingResult result,
                            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "students";
        }
        boolean isNew = student.getId() == null;
        studentService.save(student);
        redirectAttributes.addFlashAttribute("message", 
            isNew ? "Студента додано успішно!" : "Студента оновлено успішно!");
        return "redirect:/students";
    }

    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        studentService.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "Студента видалено успішно!");
        return "redirect:/students";
    }

    @GetMapping("/filter")
    public String filterStudentsByAge(@RequestParam(required = false) Integer age, Model model, RedirectAttributes redirectAttributes) {
        if (age == null) {
            redirectAttributes.addFlashAttribute("error", "Будь ласка, введіть мінімальний вік для фільтрації");
            return "redirect:/students";
        }
        
        model.addAttribute("students", studentService.findByAgeGreaterThan(age));
        model.addAttribute("student", new Student());
        return "students";
    }
} 