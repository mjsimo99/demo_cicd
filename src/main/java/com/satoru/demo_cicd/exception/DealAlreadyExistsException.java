package com.satoru.demo_cicd.exception;

public class DealAlreadyExistsException extends RuntimeException {

    public DealAlreadyExistsException(String message) {
        super(message);
    }

}

