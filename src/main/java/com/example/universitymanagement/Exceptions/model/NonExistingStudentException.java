package com.example.universitymanagement.Exceptions.model;

public class NonExistingStudentException extends StudentException{
    public NonExistingStudentException(String message) {
        super(message);
    }

    public NonExistingStudentException() {
        super("no such student");
    }
}
