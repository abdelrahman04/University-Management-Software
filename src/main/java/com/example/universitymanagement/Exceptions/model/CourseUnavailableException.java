package com.example.universitymanagement.Exceptions.model;

public class CourseUnavailableException extends SystemException{
    public CourseUnavailableException() {
        super("Course not present");
    }

    public CourseUnavailableException(String message) {
        super(message);
    }
}
