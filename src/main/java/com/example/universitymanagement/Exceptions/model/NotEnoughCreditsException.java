package com.example.universitymanagement.Exceptions.model;

public class NotEnoughCreditsException extends StudentException {
    public NotEnoughCreditsException(String message) {
        super(message);
    }

    public NotEnoughCreditsException() {
        super("You don't have enough money");
    }
}
