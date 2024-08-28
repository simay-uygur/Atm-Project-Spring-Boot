package com.simayuygur.atmprojectspringboot.exception;

import java.io.Serializable;

public class UserNotFoundException extends RuntimeException implements Serializable {


    private static final Long serialVersionUID = 1L;

    public UserNotFoundException(String message) {
        super(message);
    }
}