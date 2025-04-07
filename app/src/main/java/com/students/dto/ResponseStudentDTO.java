package com.students.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Schema(description = "DTO for student response")
public class ResponseStudentDTO {
    @Schema(description = "Student's ID", example = "1")
    private Long id;
    
    @Schema(description = "Student's name", example = "John Doe")
    @NotNull(message = "Name is required")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;
    
    @Schema(description = "Student's age", example = "20")
    @NotNull(message = "Age is required")
    @Min(value = 0, message = "Age must be greater than or equal to 0")
    @Max(value = 150, message = "Age must be less than or equal to 150")
    private Integer age;
    
    @Schema(description = "Response timestamp")
    private LocalDateTime responseDate = LocalDateTime.now();

    @NotNull(message = "Grade is required")
    @Min(value = 0, message = "Grade must be greater than or equal to 0")
    @Max(value = 100, message = "Grade must be less than or equal to 100")
    private Double grade;

    public ResponseStudentDTO() {
    }

    public ResponseStudentDTO(Long id, String name, Integer age, Double grade) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.grade = grade;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }
} 