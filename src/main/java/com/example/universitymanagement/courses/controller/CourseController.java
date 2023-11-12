package com.example.universitymanagement.courses.controller;

import com.example.universitymanagement.Exceptions.model.NonExistentDoctorException;
import com.example.universitymanagement.courses.model.Course;
import com.example.universitymanagement.courses.service.CourseService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("CourseController")
@RequestMapping("/courses")
@AllArgsConstructor
public class CourseController {
    private final CourseService courseService;

    @PostMapping("/getAllCourses")
    public ResponseEntity<List<Course>> getAllCourses(
            @RequestParam String email,
            @RequestParam String type
    ) throws NonExistentDoctorException {
        return ResponseEntity
                .status(200)
                .body(courseService.getAllCourses(email,type));
    }
    @PostMapping("/getMyCourses")
    public ResponseEntity<List<Course>> getMyCourses(
            @RequestParam String email,
            @RequestParam String type
    ){
        return ResponseEntity
                .status(200)
                .body(courseService.getMyCourses(email,type));
    }


}
