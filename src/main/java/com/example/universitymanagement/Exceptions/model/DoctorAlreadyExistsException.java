package com.example.universitymanagement.Exceptions.model;

public class DoctorAlreadyExistsException extends DoctorException {
    public DoctorAlreadyExistsException() {
        super("doctor already exists");
    }

    public DoctorAlreadyExistsException(String message) {
        super(message);
    }
}
