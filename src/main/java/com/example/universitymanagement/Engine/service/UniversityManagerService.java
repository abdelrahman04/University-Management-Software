package com.example.universitymanagement.Engine.service;

import com.example.universitymanagement.Engine.model.UniversityManager;
import com.example.universitymanagement.Engine.repository.UniversityManagerRepository;
import com.example.universitymanagement.Exceptions.model.SemesterRunningException;
import com.example.universitymanagement.Exceptions.model.SystemException;
import com.example.universitymanagement.accounts.doctor.service.DoctorService;
import com.example.universitymanagement.accounts.student.model.Student;
import com.example.universitymanagement.accounts.student.model.StudentCourse;
import com.example.universitymanagement.accounts.student.service.StudentService;
import com.example.universitymanagement.courses.model.Course;
import com.example.universitymanagement.courses.service.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class UniversityManagerService {
    private final UniversityManagerRepository universityManagerRepository ;

    public UniversityManagerService(UniversityManagerRepository universityManagerRepository) {
        this.universityManagerRepository = universityManagerRepository;
        start();
    }

    public void start(){
        UniversityManager universityManager=UniversityManager
                .builder()
                .Id("UniversityManager")
                .semesterCount(1)
                .semesterRunning(false)
                .semesterPaused(false)
                .startTime(0)
                .remainingSemesterTime(0)
                .build();
        universityManagerRepository.save(universityManager);
    }
    public Boolean isSemesterRunning(){
        return universityManagerRepository.findById("UniversityManager").orElseThrow().isSemesterRunning();
    }
    public void setSemesterRunning(boolean running){
        UniversityManager universityManager=universityManagerRepository.findById("UniversityManager").orElseThrow();
        universityManager.setSemesterRunning(running);
        universityManagerRepository.save(universityManager);
    }
    public int getSemester() {
        return universityManagerRepository.findById("UniversityManager").orElseThrow().getSemesterCount();
    }
    public void setSemester(int semester){
        UniversityManager universityManager=universityManagerRepository.findById("UniversityManager").orElseThrow();
        universityManager.setSemesterCount(semester);
        universityManagerRepository.save(universityManager);
    }
    public void setStartTime(long startTime){
        UniversityManager universityManager=universityManagerRepository.findById("UniversityManager").orElseThrow();
        universityManager.setStartTime(startTime);
    }
    public long getStartTime(){
        return universityManagerRepository.findById("UniversityManager").orElseThrow().getStartTime();
    }
    public void setRemainingSemesterTime(long remainingSemesterTime){
        UniversityManager universityManager=universityManagerRepository.findById("UniversityManager").orElseThrow();
        universityManager.setRemainingSemesterTime(remainingSemesterTime);
        universityManagerRepository.save(universityManager);
    }
    public long getRemainingSemesterTime(){
        return universityManagerRepository.findById("UniversityManager").orElseThrow().getRemainingSemesterTime();
    }


    public void setSemesterPause(boolean pause) {
        UniversityManager universityManager=universityManagerRepository.findById("UniversityManager").orElseThrow();
        universityManager.setSemesterPaused(pause);
        universityManagerRepository.save(universityManager);
    }
    public boolean isSemesterPause() {
        UniversityManager universityManager=universityManagerRepository.findById("UniversityManager").orElseThrow();
        return universityManager.isSemesterPaused();
    }

}
