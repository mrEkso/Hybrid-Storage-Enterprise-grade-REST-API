package com.example.oss.api.responses.auth;

import com.example.oss.api.dto.UserDto;
import com.example.oss.api.responses.BaseAuthResponse;
import org.springframework.http.HttpStatus;

public class LoginResponse extends BaseAuthResponse {
    public LoginResponse(UserDto user, String token) {
        super(HttpStatus.OK.value(), "login", user, token);
    }
}
