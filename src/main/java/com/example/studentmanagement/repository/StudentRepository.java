package com.example.studentmanagement.repository;

import com.example.studentmanagement.model.Student;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class StudentRepository {
    private final List<Student> students = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong();

    public List<Student> findAll() {
        return new ArrayList<>(students);
    }

    public Optional<Student> findById(Long id) {
        return students.stream()
                .filter(student -> student.getId().equals(id))
                .findFirst();
    }

    public Student save(Student student) {
        if (student.getId() == null) {
            student.setId(idCounter.incrementAndGet());
            students.add(student);
        } else {
            // Оновлення існуючого студента
            for (int i = 0; i < students.size(); i++) {
                if (students.get(i).getId().equals(student.getId())) {
                    students.set(i, student);
                    break;
                }
            }
        }
        return student;
    }

    public void deleteById(Long id) {
        students.removeIf(student -> student.getId().equals(id));
    }

    public List<Student> findByAgeGreaterThan(int age) {
        return students.stream()
                .filter(student -> student.getAge() > age)
                .collect(Collectors.toList());
    }
} 