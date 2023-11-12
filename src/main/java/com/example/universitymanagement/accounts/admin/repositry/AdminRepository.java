package com.example.universitymanagement.accounts.admin.repositry;

import com.example.universitymanagement.accounts.admin.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, String> {
    boolean existsByEmailAndPassword(String email, String password);

    Optional<Admin> findByEmailAndPassword(String email, String password);
}
