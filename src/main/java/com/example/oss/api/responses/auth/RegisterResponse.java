package com.example.oss.api.responses.auth;

import com.example.oss.api.dto.UserDto;
import com.example.oss.api.responses.BaseAuthResponse;
import org.springframework.http.HttpStatus;

public class RegisterResponse extends BaseAuthResponse {
    public RegisterResponse(UserDto user, String token) {
        super(HttpStatus.CREATED.value(), "register", user, token);
    }
}
