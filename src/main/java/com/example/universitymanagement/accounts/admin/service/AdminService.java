package com.example.universitymanagement.accounts.admin.service;

import com.example.universitymanagement.Engine.service.UniversityManagerService;
import com.example.universitymanagement.Exceptions.model.*;
import com.example.universitymanagement.accounts.admin.model.Admin;
import com.example.universitymanagement.accounts.admin.repositry.AdminRepository;
import com.example.universitymanagement.accounts.doctor.model.Doctor;
import com.example.universitymanagement.accounts.doctor.model.DoctorRequest;
import com.example.universitymanagement.accounts.doctor.service.DoctorService;
import com.example.universitymanagement.accounts.student.model.Student;
import com.example.universitymanagement.accounts.student.model.StudentCourse;
import com.example.universitymanagement.accounts.student.model.StudentRequests;
import com.example.universitymanagement.accounts.student.service.StudentService;
import com.example.universitymanagement.courses.model.Course;
import com.example.universitymanagement.courses.service.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;
    private final StudentService studentService;
    private final DoctorService doctorService;
    private final CourseService courseService;
    private final UniversityManagerService universityManagerService;
    public boolean isAdmin(String email, String password) {
        return email.equals("admin") && password.equals("admin");
    }

    public Admin login(String email, String password) throws InvalidCredentialsException {
        if(!adminRepository.existsByEmailAndPassword(email,password))
            throw new InvalidCredentialsException();
        return adminRepository
                .findByEmailAndPassword(email,password)
                .orElseThrow();
    }

    public List<StudentRequests> showStudentRequests() {
        return studentService.showRequests();
    }

    public List<DoctorRequest> showDoctorRequests() {
        return doctorService.showRequests();
    }

    public Boolean acceptStudentRequest(String id) {
        return studentService.acceptRequests(id);
    }

    public Boolean acceptDoctorRequest(List<String> emails) {
        return doctorService.acceptRequests(emails);
    }

    public Boolean rejectStudentRequest(List<String> id) {
        return studentService.rejectRequests(id);
    }

    public Boolean rejectDoctorRequest(List<String> emails) {
        return doctorService.rejectRequests(emails);
    }

    public Course addCourse(String name, int creditHours, String specialization, int maxStudents) {
        return courseService.newCourse(name,creditHours,specialization,maxStudents);
    }

    public Course modifyCourse(String name, int creditHours, String specialization, int maxStudents) throws Exception{
        return courseService.modifyCourse(name,creditHours,specialization,maxStudents);
    }

    public Student modifyStudent(String id, String email, String password, String name, Double balance) throws NonExistingStudentException {
        return studentService.modifyStudent(id,email,password,name,balance);
    }

    public Doctor modifyDoctor(String email, String password, String name, String specialization) throws NonExistentDoctorException {
        return doctorService.modifyDoctor(email,password,name,specialization);
    }

    public void startSemester() throws SemesterRunningException {
        if(universityManagerService.isSemesterRunning()){
            throw new SemesterRunningException();
        }
        List<StudentCourse> studentCourses = studentService.findStudentCourseBySemester(universityManagerService.getSemester());
        studentCourses.forEach(studentCourse -> {
            Student student=studentCourse.getId().getStudent();
            Course course= studentCourse.getId().getCourse();
            if(student.getCreditHours()>0&&student.getCreditHours()<9){
                try {
                    studentService.removeStudentCourse(student.getEmail(), course.getName());
                } catch (SystemException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        doctorService.removeAllCoursesByNoInstructor();
        universityManagerService.setSemesterRunning(true);
        universityManagerService.setStartTime(System.currentTimeMillis());
        universityManagerService.setRemainingSemesterTime(200);
        universityManagerService.setSemesterPause(false);
    }

    public void pauseSemester() throws SemesterRunningException {
        if (!universityManagerService.isSemesterRunning()|| universityManagerService.isSemesterRunning()){
            throw new SemesterRunningException();
        }
        universityManagerService.setSemesterPause(true);
        universityManagerService.setRemainingSemesterTime( universityManagerService.getRemainingSemesterTime()-(System.currentTimeMillis()- universityManagerService.getStartTime())/1000);
        universityManagerService.setStartTime(System.currentTimeMillis());
    }
    public void continueSemester() throws SemesterRunningException {
        if(!universityManagerService.isSemesterRunning())
            throw new SemesterRunningException();
        universityManagerService.setSemesterPause(false);
        universityManagerService.setStartTime(System.currentTimeMillis());
    }

    public void endSemester() throws SemesterRunningException {
        if(!universityManagerService.isSemesterRunning()){
            throw new SemesterRunningException("No semester running");
        }
        studentService.randomizeGrades();
        universityManagerService.setSemesterRunning(false);
        universityManagerService.setSemester(universityManagerService.getSemester()+1);
    }

    @Scheduled(cron = "0 * * ? * *")
    public void scheduleFixedRateTask() throws SemesterRunningException {

        if(!universityManagerService.isSemesterRunning()|| universityManagerService.isSemesterPause())
            return;
        universityManagerService.setRemainingSemesterTime( universityManagerService.getRemainingSemesterTime()-(System.currentTimeMillis()- universityManagerService.getStartTime())/1000);
        universityManagerService.setStartTime(System.currentTimeMillis());
        if(universityManagerService.getRemainingSemesterTime() <=0){
            universityManagerService.setRemainingSemesterTime(0);
            endSemester();
            return;
        }
    }

}
