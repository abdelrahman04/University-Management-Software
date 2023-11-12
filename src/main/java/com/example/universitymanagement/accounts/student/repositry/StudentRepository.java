package com.example.universitymanagement.accounts.student.repositry;

import com.example.universitymanagement.accounts.student.model.Student;
import com.example.universitymanagement.accounts.student.model.StudentCourse;
import com.example.universitymanagement.accounts.student.model.StudentRequests;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student,String> {
    boolean existsById(String id);
    boolean existsByEmailAndPassword(String email, String password);
    boolean existsByEmail(String email);
    Student findByEmail(String email);
    Student findByEmailAndPassword(String email,String password);




}
