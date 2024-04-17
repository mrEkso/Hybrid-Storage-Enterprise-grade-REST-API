package com.example.oss.api.responses.models.auth;

import com.example.oss.api.dto.UserDto;
import com.example.oss.api.responses.models.BaseAuthResponse;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class LoginResponse extends BaseAuthResponse {
    public LoginResponse(UserDto user, String token) {
        super(HttpStatus.OK.value(), "login", user, token);
    }
}
