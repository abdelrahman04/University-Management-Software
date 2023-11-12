package com.example.universitymanagement.accounts.student.controller;

import com.example.universitymanagement.Exceptions.model.InvalidCredentialsException;
import com.example.universitymanagement.Exceptions.model.NonExistingStudentException;
import com.example.universitymanagement.Exceptions.model.StudentAlreadyExistsException;
import com.example.universitymanagement.Exceptions.model.SystemException;
import com.example.universitymanagement.accounts.student.model.Student;
import com.example.universitymanagement.accounts.student.model.StudentCourse;
import com.example.universitymanagement.accounts.student.model.StudentRequests;
import com.example.universitymanagement.accounts.student.service.StudentService;
import com.example.universitymanagement.courses.model.Course;
import com.example.universitymanagement.courses.model.Grade;
import lombok.AllArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Stack;

@RestController()
@RequestMapping("/students")
@AllArgsConstructor
public class StudentController {
    private final StudentService studentService;
    @PostMapping("/add")
    public ResponseEntity<StudentRequests> newStudent(
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String name,
            @RequestParam Double balance
    ) throws StudentAlreadyExistsException {
        return ResponseEntity
                .status(201)
                .body(studentService
                        .newStudent(
                                email,
                                password,
                                name,
                                balance
                        )
                );
    }



    @PostMapping("/login")
    public ResponseEntity<Student> loginStudent(
            @RequestParam String email,
            @RequestParam String password
    ) throws InvalidCredentialsException {
        return ResponseEntity
                .status(201)
                .body(studentService
                        .loginStudent(
                                email,
                                password
                        )
                );
    }
    @PostMapping("/registerCourse")
    public ResponseEntity<StudentCourse> registerCourse(
            @RequestParam String email,
            @RequestParam String courseName
    ) throws SystemException {
        return ResponseEntity
                .status(201)
                .body(studentService
                        .registerCourse(
                                email,
                                courseName
                        )
                );
    }

    @PostMapping("/showMyCourses")
    public ResponseEntity<List<Course>> showMyCourses(
            @RequestParam String email
    ) throws NonExistingStudentException {
        return ResponseEntity
                .status(201)
                .body(studentService
                        .showMyCourses(
                                email
                        )
                );
    }
    @PostMapping("/showAllCourses")
    public ResponseEntity<List<Course>> showCourses(){
        return ResponseEntity
                .status(201)
                .body(studentService
                        .showAllCourses()
                );
    }
    @PostMapping("/showGrades")
    public ResponseEntity<List<Pair<Course, Grade>>> showGrades(
            @RequestParam String email,
            @RequestParam int semester
    ) throws NonExistingStudentException {
        return ResponseEntity
                .status(201)
                .body(studentService
                        .showGrades(
                                email,
                                semester
                        )
                );
    }


}
