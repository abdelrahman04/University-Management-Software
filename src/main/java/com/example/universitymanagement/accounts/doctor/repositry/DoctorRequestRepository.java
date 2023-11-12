package com.example.universitymanagement.accounts.doctor.repositry;

import com.example.universitymanagement.accounts.doctor.model.DoctorRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoctorRequestRepository extends JpaRepository<DoctorRequest, String> {
    DoctorRequest findByEmail(String email);

    void deleteAllByEmailIn(List<String> emails);

    boolean existsByEmail(String email);
}
