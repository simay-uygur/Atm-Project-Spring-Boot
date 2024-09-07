package com.simayuygur.atmprojectspringboot.jwt;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthValidationResponse {
    private String message;

    public AuthValidationResponse(String message) {
        this.message = message;
    }

}
