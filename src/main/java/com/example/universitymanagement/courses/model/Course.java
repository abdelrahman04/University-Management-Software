package com.example.universitymanagement.courses.model;

import com.example.universitymanagement.accounts.doctor.model.Doctor;
import com.example.universitymanagement.accounts.doctor.model.DoctorTeachesCourse;
import com.example.universitymanagement.accounts.student.model.StudentCourse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.mapping.Join;

import javax.print.Doc;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Course {

    @Id
    private String name;
    private int creditHours;
    private String specialization;
    private int maxStudents;
    @OneToMany(fetch=FetchType.LAZY)
    private List<DoctorTeachesCourse> doctor;
    @OneToMany(fetch = FetchType.LAZY)
    private List<StudentCourse> studentCourses;

}
