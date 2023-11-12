package com.example.universitymanagement.courses.repositry;

import com.example.universitymanagement.courses.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, String> {
    List<Course> findAllByStudentCourses_Id_Student_Email(String email);
    List<Course> findAllBySpecialization(String specialization);

    List<Course> findAllByDoctor_Id_Doctor_Email(String email);

    @Override
    Optional<Course> findById(String s);

    Optional<Course> findByName(String courseName);
    boolean existsByName(String name);
}
