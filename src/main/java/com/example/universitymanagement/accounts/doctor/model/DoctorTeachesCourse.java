package com.example.universitymanagement.accounts.doctor.model;
import com.example.universitymanagement.courses.model.Course;
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
public class DoctorTeachesCourse {
    @EmbeddedId
    private DoctorTeachesCourseId id;



}