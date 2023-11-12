package com.example.universitymanagement.Exceptions.model;

public class StudentAlreadyExistsException extends StudentException{
    public StudentAlreadyExistsException(String message) {
        super(message);
    }

    public StudentAlreadyExistsException() {
        super("Email exists before");
    }
}
