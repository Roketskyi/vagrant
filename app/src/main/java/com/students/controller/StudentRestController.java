package com.students.controller;

import com.students.dto.RequestStudentDTO;
import com.students.dto.ResponseStudentDTO;
import com.students.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/students")
@Tag(name = "Student Management", description = "APIs for managing students")
public class StudentRestController {
    private final StudentService studentService;

    public StudentRestController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    @Operation(summary = "Get all students", description = "Retrieves a list of all students")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved students"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<ResponseStudentDTO>> getAllStudents() {
        log.info("GET request to fetch all students");
        List<ResponseStudentDTO> students = studentService.getAllStudents();
        log.info("Returning {} students", students.size());
        return ResponseEntity.ok(students);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get student by ID", description = "Retrieves a specific student by their ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved student"),
        @ApiResponse(responseCode = "404", description = "Student not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ResponseStudentDTO> getStudentById(
        @Parameter(description = "Student ID", required = true) @PathVariable Long id) {
        log.info("GET request to fetch student with id: {}", id);
        ResponseStudentDTO student = studentService.getStudentById(id);
        log.info("Retrieved student: {}", student.getName());
        return ResponseEntity.ok(student);
    }

    @PostMapping
    @Operation(summary = "Create student", description = "Creates a new student")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Student created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ResponseStudentDTO> createStudent(
        @Valid @RequestBody RequestStudentDTO requestDTO) {
        log.info("POST request to create student: {}", requestDTO.getName());
        ResponseStudentDTO createdStudent = studentService.createStudent(requestDTO);
        log.info("Created student with id: {}", createdStudent.getId());
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(createdStudent);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update student", description = "Updates an existing student")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Student updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "404", description = "Student not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ResponseStudentDTO> updateStudent(
        @Parameter(description = "Student ID", required = true) @PathVariable Long id,
        @Valid @RequestBody RequestStudentDTO requestDTO) {
        log.info("PUT request to update student with id: {}", id);
        ResponseStudentDTO updatedStudent = studentService.updateStudent(id, requestDTO);
        log.info("Updated student: {}", updatedStudent.getName());
        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete student", description = "Deletes a student by their ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Student deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Student not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> deleteStudent(
        @Parameter(description = "Student ID", required = true) @PathVariable Long id) {
        log.info("DELETE request to remove student with id: {}", id);
        studentService.deleteStudent(id);
        log.info("Deleted student with id: {}", id);
        return ResponseEntity.noContent().build();
    }
} 