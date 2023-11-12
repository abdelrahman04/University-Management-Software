package com.example.universitymanagement.Exceptions.model;

public class NonExistentDoctorException extends DoctorException{
    public NonExistentDoctorException() {
        super("Non Existent Doctor");
    }

    public NonExistentDoctorException(String message) {
        super(message);
    }
}
