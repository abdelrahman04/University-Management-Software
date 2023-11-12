package com.example.universitymanagement.accounts.doctor.model;

import com.example.universitymanagement.courses.model.Course;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class DoctorTeachesCourseId implements Serializable {
    @ManyToOne
    @JoinColumn(name = "doctor_email",referencedColumnName = "email")
    private Doctor doctor;
    @ManyToOne
    @JoinColumn(name = "course_name",referencedColumnName = "name")
    private Course course;
    private int semester;


}
