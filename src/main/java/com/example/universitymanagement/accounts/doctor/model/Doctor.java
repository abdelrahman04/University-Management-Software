package com.example.universitymanagement.accounts.doctor.model;
import com.example.universitymanagement.courses.model.Course;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Doctor {
    @Id
    private String email;
    private String password;
    private String name;
    private String specialization;
    @OneToMany()
    private List<DoctorTeachesCourse> courses;

}
