package com.example.oss.api.responses.models.auth;

import com.example.oss.api.dto.UserDto;
import com.example.oss.api.responses.models.BaseAuthResponse;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class RegisterResponse extends BaseAuthResponse {
    public RegisterResponse(UserDto user, String token) {
        super(HttpStatus.CREATED.value(), "register", user, token);
    }
}
