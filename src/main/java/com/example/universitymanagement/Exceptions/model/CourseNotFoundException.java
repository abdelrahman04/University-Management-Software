package com.example.universitymanagement.Exceptions.model;

public class CourseNotFoundException extends SystemException{
    public CourseNotFoundException() {
        super("No course with this name");
    }

    public CourseNotFoundException(String message) {
        super(message);
    }
}
