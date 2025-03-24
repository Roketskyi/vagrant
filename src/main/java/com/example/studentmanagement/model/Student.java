package com.example.studentmanagement.model;

import lombok.Data;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class Student {
    private Long id;
    
    @NotNull(message = "Ім'я не може бути порожнім")
    @Size(min = 2, max = 50, message = "Ім'я має бути від 2 до 50 символів")
    private String name;
    
    @NotNull(message = "Вік не може бути порожнім")
    private Integer age;
} 