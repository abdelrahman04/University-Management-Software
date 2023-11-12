package com.example.universitymanagement.accounts.doctor.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class DoctorRequest {
    @Id
    private String email;
    private String password;
    private String name;
    private String specialization;
}
