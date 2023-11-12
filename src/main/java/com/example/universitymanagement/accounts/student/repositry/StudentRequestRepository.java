package com.example.universitymanagement.accounts.student.repositry;

import com.example.universitymanagement.accounts.student.model.StudentRequests;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRequestRepository extends JpaRepository<StudentRequests, String> {
    void deleteAllByIdIn(List<String> id);

    boolean existsByEmail(String email);
}
