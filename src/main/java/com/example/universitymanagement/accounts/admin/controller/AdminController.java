package com.example.universitymanagement.accounts.admin.controller;

import com.example.universitymanagement.Exceptions.model.InvalidCredentialsException;
import com.example.universitymanagement.Exceptions.model.NonExistentDoctorException;
import com.example.universitymanagement.Exceptions.model.NonExistingStudentException;
import com.example.universitymanagement.Exceptions.model.SystemException;
import com.example.universitymanagement.accounts.admin.model.Admin;
import com.example.universitymanagement.accounts.admin.service.AdminService;
import com.example.universitymanagement.accounts.doctor.model.Doctor;
import com.example.universitymanagement.accounts.doctor.model.DoctorRequest;
import com.example.universitymanagement.accounts.student.model.Student;
import com.example.universitymanagement.accounts.student.model.StudentRequests;
import com.example.universitymanagement.courses.model.Course;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/Admin")
@AllArgsConstructor
public class AdminController {
    private final AdminService adminService;
    @PostMapping("/login")
    public ResponseEntity<Admin> loginAdmin(
            @RequestParam String email,
            @RequestParam String password
    ) throws InvalidCredentialsException {
        return ResponseEntity
                .status(201)
                .body(adminService
                        .login(
                                email,
                                password
                        )
                );
    }

    @GetMapping("/showStudentRequests")
    public ResponseEntity<List<StudentRequests>> showStudentRequest(){
        return ResponseEntity
                .status(201)
                .body(adminService.showStudentRequests());
    }
    @GetMapping("/showDoctorRequests")
    public ResponseEntity<List<DoctorRequest>> showDoctorRequest(){
        return ResponseEntity
                .status(201)
                .body(adminService.showDoctorRequests());
    }
    @PostMapping("/acceptStudentRequests")
    public ResponseEntity<Boolean> acceptStudentRequest(
            @RequestParam String id
    ){
        return ResponseEntity
                .status(201)
                .body(adminService.acceptStudentRequest(id));
    }
    @PostMapping("/acceptDoctorRequests")
    public ResponseEntity<Boolean> acceptDoctorRequest(
            @RequestParam List<String> emails
    ){
        return ResponseEntity
                .status(201)
                .body(adminService.acceptDoctorRequest(emails));
    }

    @DeleteMapping("/rejectStudentRequests")
    public ResponseEntity<Boolean> rejectStudentRequest(
            @RequestParam List<String> id
    ){
        return ResponseEntity
                .status(201)
                .body(adminService.rejectStudentRequest(id));
    }
    @DeleteMapping ("/rejectDoctorRequests")
    public ResponseEntity<Boolean> rejectDoctorRequest(
            @RequestParam List<String> emails
    ){
        return ResponseEntity
                .status(201)
                .body(adminService.rejectDoctorRequest(emails));
    }
    @PostMapping("/addCourse")
    public ResponseEntity<Course> addCourse(
            @RequestParam String name,
            @RequestParam int creditHours,
            @RequestParam String specialization,
            @RequestParam int maxStudents
    ){
        return ResponseEntity
                .status(201)
                .body(adminService.addCourse(
                        name,
                        creditHours,
                        specialization,
                        maxStudents
                ));
    }

    @PutMapping("/ModifyCourse")
    public ResponseEntity<Course> ModifyCourse (
            @RequestParam String originalName,
            @RequestParam String name,
            @RequestParam int creditHours,
            @RequestParam String specialization,
            @RequestParam int maxStudents
    )throws Exception{
        return ResponseEntity
                .status(201)
                .body(adminService.modifyCourse(
                        name,
                        creditHours,
                        specialization,
                        maxStudents
                ));
    }
    @PutMapping("/ModifyStudent")
    public ResponseEntity<Student> modifyStudent(
            @RequestParam String originalEmail,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String name,
            @RequestParam Double balance
    ) throws NonExistingStudentException {
        return ResponseEntity
                .status(201)
                .body(adminService
                        .modifyStudent(
                                originalEmail,
                                email,
                                password,
                                name,
                                balance
                        )
                );
    }
    @PutMapping("/modifyDoctor")
    public ResponseEntity<Doctor> modifyDoctor(
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String name,
            @RequestParam String specialization
    ) throws NonExistentDoctorException {
        return ResponseEntity
                .status(201)
                .body(adminService
                        .modifyDoctor(
                                email,
                                password,
                                name,
                                specialization
                        )
                );
    }

    @PostMapping("/startSemester")
    public void startSemester() throws SystemException {
        adminService.startSemester();
    }

    @PostMapping("/endSemester")
    public void endSemester() throws SystemException {
        adminService.endSemester();
    }

    @PostMapping("/pauseSemester")
    public void pauseSemester() throws SystemException {
        adminService.pauseSemester();
    }

    @PostMapping("/continueSemester")
    public void continueSemester() throws SystemException {
        adminService.continueSemester();
    }


}
