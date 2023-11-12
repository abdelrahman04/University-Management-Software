package com.example.universitymanagement.Engine.controller;

import com.example.universitymanagement.Engine.service.UniversityManagerService;
import com.example.universitymanagement.Exceptions.model.SemesterRunningException;
import com.example.universitymanagement.Exceptions.model.SystemException;
import com.example.universitymanagement.accounts.doctor.service.DoctorService;
import com.example.universitymanagement.accounts.student.model.Student;
import com.example.universitymanagement.accounts.student.model.StudentCourse;
import com.example.universitymanagement.accounts.student.service.StudentService;
import com.example.universitymanagement.courses.model.Course;
import com.example.universitymanagement.courses.service.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("UniversityController")
@RequestMapping("/Management")
@AllArgsConstructor
public class UniversityManagerController {



}
