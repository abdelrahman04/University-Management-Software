package com.example.universitymanagement.Exceptions.model;

public class CourseHasInstructorException extends DoctorException {
    public CourseHasInstructorException() {
        super("Course already has a Teacher");
    }

    public CourseHasInstructorException(String message) {
        super(message);
    }
}
