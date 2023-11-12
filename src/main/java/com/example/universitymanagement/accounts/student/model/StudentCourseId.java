package com.example.universitymanagement.accounts.student.model;

import com.example.universitymanagement.courses.model.Course;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentCourseId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "student_id",referencedColumnName = "id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "course_name",referencedColumnName = "name")
    private Course course;

    private int semester;
    // constructors, getters, and setters
}