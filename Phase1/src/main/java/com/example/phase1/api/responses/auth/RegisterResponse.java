package com.example.phase1.api.responses.auth;

import com.example.phase1.api.dto.UserDto;
import com.example.phase1.api.responses.BaseAuthResponse;
import org.springframework.http.HttpStatus;

public class RegisterResponse extends BaseAuthResponse {
    public RegisterResponse(UserDto user, String token) {
        super(HttpStatus.CREATED.value(), "register", user, token);
    }
}
