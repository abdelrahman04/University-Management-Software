package com.example.universitymanagement.accounts.doctor.repositry;

import com.example.universitymanagement.accounts.doctor.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor,String> {

    Optional<Doctor> findByEmail(String s);


    boolean existsByEmailAndPassword(String email, String password);

    Optional<Doctor> findByEmailAndPassword(String email, String password);

    boolean existsByEmail(String email);
}
