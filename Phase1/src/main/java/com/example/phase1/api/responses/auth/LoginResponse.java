package com.example.phase1.api.responses.auth;

import com.example.phase1.api.dto.UserDto;
import com.example.phase1.api.responses.BaseAuthResponse;
import org.springframework.http.HttpStatus;

public class LoginResponse extends BaseAuthResponse {
    public LoginResponse(UserDto user, String token) {
        super(HttpStatus.OK.value(), "login", user, token);
    }
}
