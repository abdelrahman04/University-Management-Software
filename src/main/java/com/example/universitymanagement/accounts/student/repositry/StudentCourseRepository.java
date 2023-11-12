package com.example.universitymanagement.accounts.student.repositry;

import com.example.universitymanagement.accounts.student.model.Student;
import com.example.universitymanagement.accounts.student.model.StudentCourse;
import com.example.universitymanagement.accounts.student.model.StudentCourseId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;

public interface StudentCourseRepository extends JpaRepository<StudentCourse, StudentCourseId> {
    ArrayList<StudentCourse> findAllById_StudentAndId_Semester(Student student, int semester);
    List<StudentCourse> findAllById_Course_NameAndId_Semester(String courseName, int semester);

    int countAllById_Course_NameAndId_Semester(String courseName, int semester);

    boolean existsById_Student_EmailAndId_Course_Name(String email, String courseName);

    StudentCourse findById_Student_EmailAndId_Course_Name(String email, String courseName);

    void deleteAll(Iterable<? extends StudentCourse> entities);
    List<StudentCourse> findAllById_Semester(int semester);


    boolean existsById_Student_EmailAndId_Course_NameAndId_Semester(String email, String courseName, int semester);
}
