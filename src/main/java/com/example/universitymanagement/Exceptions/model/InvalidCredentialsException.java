package com.example.universitymanagement.Exceptions.model;

public class InvalidCredentialsException extends SystemException{
    public InvalidCredentialsException() {
        super("Invalid Credentials");
    }

    public InvalidCredentialsException(String message) {
        super(message);
    }
}
