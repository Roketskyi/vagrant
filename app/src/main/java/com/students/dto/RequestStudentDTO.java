package com.students.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "DTO for creating or updating a student")
public class RequestStudentDTO {
    
    @NotNull(message = "Name is required")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    @Schema(description = "Student's name (2-50 characters)", example = "John Doe")
    private String name;

    @NotNull(message = "Age is required")
    @Min(value = 0, message = "Age must be greater than or equal to 0")
    @Max(value = 150, message = "Age must be less than or equal to 150")
    @Schema(description = "Student's age (0-150)", example = "20")
    private Integer age;

    @NotNull(message = "Grade is required")
    @Min(value = 0, message = "Grade must be greater than or equal to 0")
    @Max(value = 100, message = "Grade must be less than or equal to 100")
    @Schema(description = "Student's grade (0-100)", example = "85.5")
    private Double grade;

    public RequestStudentDTO() {
    }

    public RequestStudentDTO(String name, Integer age, Double grade) {
        this.name = name;
        this.age = age;
        this.grade = grade;
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