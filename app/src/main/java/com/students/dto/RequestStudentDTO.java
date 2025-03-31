package com.students.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO for creating or updating a student")
public class RequestStudentDTO {
    
    @NotNull(message = "Name cannot be null")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    @Pattern(regexp = "^(?!\\d+$)[a-zA-Z\\s]+$", message = "Name cannot contain only numbers")
    @Schema(description = "Student's name (2-50 characters, cannot contain only numbers)", example = "John Doe")
    private String name;

    @NotNull(message = "Age cannot be null")
    @Min(value = 16, message = "Age must be at least 16")
    @Max(value = 100, message = "Age must not be greater than 100")
    @Schema(description = "Student's age (16-100)", example = "20")
    private Integer age;
} 