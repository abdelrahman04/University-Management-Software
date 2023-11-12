package com.example.universitymanagement.accounts.student.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Student {
    @Id
    @GeneratedValue(generator = "uuid")
    private int id;
    private String email;
    private String password;
    private String name;
    private double balance;
    private boolean financialAided;
    private int creditHours;
    @OneToMany()
    private List<StudentCourse> studentCourses;
}
