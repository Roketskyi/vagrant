package com.students.controller;

import com.students.dto.RequestStudentDTO;
import com.students.dto.ResponseStudentDTO;
import com.students.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
@Tag(name = "Student Management API", description = "Operations pertaining to students")
public class StudentRestController {

    private final StudentService studentService;

    @GetMapping
    @Operation(summary = "Get all students", description = "Retrieves a list of all students in the system")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of students")
    public List<ResponseStudentDTO> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a student by ID", description = "Retrieves a specific student by their ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved student"),
        @ApiResponse(responseCode = "404", description = "Student not found")
    })
    public ResponseStudentDTO getStudentById(
            @Parameter(description = "ID of the student to retrieve", example = "1")
            @PathVariable Long id) {
        return studentService.getStudentById(id);
    }

    @PostMapping
    @Operation(summary = "Create a new student", description = "Creates a new student with the provided information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Student successfully created"),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<ResponseStudentDTO> createStudent(
            @Parameter(description = "Student information to create")
            @Valid @RequestBody RequestStudentDTO studentDTO) {
        ResponseStudentDTO createdStudent = studentService.createStudent(studentDTO);
        return new ResponseEntity<>(createdStudent, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing student", description = "Updates the information of an existing student")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Student successfully updated"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "404", description = "Student not found")
    })
    public ResponseStudentDTO updateStudent(
            @Parameter(description = "ID of the student to update", example = "1")
            @PathVariable Long id,
            @Parameter(description = "Updated student information")
            @Valid @RequestBody RequestStudentDTO studentDTO) {
        return studentService.updateStudent(id, studentDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a student", description = "Deletes a student from the system")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Student successfully deleted"),
        @ApiResponse(responseCode = "404", description = "Student not found")
    })
    public ResponseEntity<Void> deleteStudent(
            @Parameter(description = "ID of the student to delete", example = "1")
            @PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
} 