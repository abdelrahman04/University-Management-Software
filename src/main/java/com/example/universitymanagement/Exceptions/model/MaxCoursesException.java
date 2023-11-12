package com.example.universitymanagement.Exceptions.model;

public class MaxCoursesException extends DoctorException {
    public MaxCoursesException() {
        super("max enrolled students reached");
    }

    public MaxCoursesException(String message) {
        super(message);
    }
}
