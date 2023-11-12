package com.example.universitymanagement.Engine.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UniversityManager {
    @Id
    private String Id;
    private Integer semesterCount=1;
    private boolean semesterRunning=false;
    private long startTime;
    private long remainingSemesterTime;
    private boolean semesterPaused=false;
}
