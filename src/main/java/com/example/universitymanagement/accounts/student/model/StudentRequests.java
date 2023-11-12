package com.example.universitymanagement.accounts.student.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
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
public class StudentRequests {

        @Id
        @GeneratedValue(generator = "uuid")
        private int id;
        private String email;
        private String password;
        private String name;
        private double balance;
}
