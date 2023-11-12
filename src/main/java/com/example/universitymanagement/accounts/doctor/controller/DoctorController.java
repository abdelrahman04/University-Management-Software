package com.example.universitymanagement.accounts.doctor.controller;

import com.example.universitymanagement.Exceptions.model.*;
import com.example.universitymanagement.accounts.doctor.model.Doctor;
import com.example.universitymanagement.accounts.doctor.model.DoctorRequest;
import com.example.universitymanagement.accounts.doctor.service.DoctorService;
import com.example.universitymanagement.courses.model.Course;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/doctor")
@AllArgsConstructor
public class DoctorController {
    private final DoctorService doctorService;

    @PostMapping("/add")
    public ResponseEntity<DoctorRequest> newDoctor(
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String name,
            @RequestParam String specialization
    ) throws DoctorAlreadyExistsException {
        return ResponseEntity
                .status(201)
                .body(doctorService
                        .newDoctor(
                                email,
                                password,
                                name,
                                specialization
                        )
                );
    }

    @PostMapping("/login")
    public ResponseEntity<Doctor> loginDoctor(
            @RequestParam String email,
            @RequestParam String password
    ) throws InvalidCredentialsException {
        return ResponseEntity
                .status(201)
                .body(doctorService
                        .loginDoctor(
                                email,
                                password
                        )
                );
    }

    @PostMapping("/showAllCourses")
    public ResponseEntity<List<Course>> showAllCourses(String email) throws NonExistentDoctorException {
        return ResponseEntity
                .status(201)
                .body(doctorService
                        .showAllCourses(
                                email
                        )
                );
    }

    @PostMapping("/showMyCourses")
    public ResponseEntity<List<Course>> showMyCourses(String email) throws NonExistentDoctorException {
        return ResponseEntity
                .status(201)
                .body(doctorService
                        .showMyCourses(
                                email
                        )
                );
    }

    @PostMapping("/registerCourse")
    public ResponseEntity<Boolean> registerCourse(
            @RequestParam String email,
            @RequestParam String courseName
    ) throws SystemException {
        return ResponseEntity
                .status(201)
                .body(doctorService
                        .registerCourse(
                                email,
                                courseName
                        )
                );
    }

    @PostMapping("/removeCourse")
    public ResponseEntity<Boolean> removeCourse(
            @RequestParam String email,
            @RequestParam String courseName
    ) throws NonExistentCourseException, NonExistentDoctorException, SemesterRunningException {
        return ResponseEntity
                .status(201)
                .body(doctorService
                        .removeDoctorCourse(
                                email,
                                courseName
                        )
                );
    }


}
