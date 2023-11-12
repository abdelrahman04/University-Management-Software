package com.example.universitymanagement.accounts.student.model;

import com.example.universitymanagement.courses.model.Course;
import com.example.universitymanagement.courses.model.Grade;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class StudentCourse {
    @EmbeddedId
    private StudentCourseId id;

    private Grade grade;
}

