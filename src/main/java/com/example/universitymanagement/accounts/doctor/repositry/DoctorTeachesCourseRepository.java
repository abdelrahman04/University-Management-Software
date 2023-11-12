package com.example.universitymanagement.accounts.doctor.repositry;

import com.example.universitymanagement.accounts.doctor.model.DoctorTeachesCourse;
import com.example.universitymanagement.accounts.doctor.model.DoctorTeachesCourseId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoctorTeachesCourseRepository extends JpaRepository<DoctorTeachesCourse, DoctorTeachesCourseId> {

    List<DoctorTeachesCourse> findAllById_Doctor_EmailAndId_Semester(String email, int semester);

    boolean existsById_SemesterAndId_Course_Name(int semester, String courseName);

    int countAllById_Doctor_EmailAndId_Semester(String email, int semester);
    void deleteById(DoctorTeachesCourseId id);

    boolean existsById_Doctor_EmailAndId_Course_NameAndId_Semester(String email, String courseName, int semester);

}
