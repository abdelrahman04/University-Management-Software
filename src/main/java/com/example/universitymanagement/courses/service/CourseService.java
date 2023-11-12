package com.example.universitymanagement.courses.service;

import com.example.universitymanagement.Engine.service.UniversityManagerService;
import com.example.universitymanagement.Exceptions.model.CourseNotFoundException;
import com.example.universitymanagement.Exceptions.model.NonExistentCourseException;
import com.example.universitymanagement.Exceptions.model.NonExistentDoctorException;
import com.example.universitymanagement.accounts.doctor.model.Doctor;
import com.example.universitymanagement.accounts.doctor.repositry.DoctorRepository;
import com.example.universitymanagement.accounts.doctor.repositry.DoctorTeachesCourseRepository;
import com.example.universitymanagement.accounts.doctor.service.DoctorService;
import com.example.universitymanagement.accounts.student.service.StudentService;
import com.example.universitymanagement.courses.model.Course;
import com.example.universitymanagement.courses.repositry.CourseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final DoctorRepository doctorService;
    public Course newCourse(
            String name,
            int creditHours,
            String specialization,
            int maxStudents
    ) {
        Course course = Course
                .builder()
                .name(name)
                .creditHours(creditHours)
                .specialization(specialization)
                .maxStudents(maxStudents)
                .build();

        return courseRepository.save(course);
    }

    public List<Course> getAllCourses(String email, String type) throws NonExistentDoctorException {
        if(type.equals("doctor")) {
            Doctor doctor = doctorService.findByEmail(email).orElseThrow(NonExistentDoctorException::new);
            return courseRepository.findAllBySpecialization(doctor.getSpecialization());
        }else
            return courseRepository.findAll();
    }
    public List<Course> getMyCourses(String email,String type){
        if(type.equals("doctor"))
            return courseRepository.findAllByDoctor_Id_Doctor_Email(email);
        else if(type.equals("student"))
            return courseRepository.findAllByStudentCourses_Id_Student_Email(email);
        else
            return null;
    }
    public Course findByName(String name) throws NonExistentCourseException {
        return courseRepository.findByName(name).orElseThrow(()->new NonExistentCourseException());
    }

    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    public List<Course> findAllBySpecialization(String specialization) {
        return courseRepository.findAllBySpecialization(specialization);
    }

    public Course modifyCourse(String name, int creditHours, String specialization, int maxStudents) throws Exception{
        Course course = courseRepository.findByName(name).orElseThrow(()->new NonExistentCourseException());
        if(course == null)
            throw new CourseNotFoundException();
        course.setCreditHours(creditHours);
        course.setSpecialization(specialization);
        course.setMaxStudents(maxStudents);
        return courseRepository.save(course);
    }



    public boolean existsByCourseName(String courseName) {
        return courseRepository.existsByName(courseName);
    }
}
