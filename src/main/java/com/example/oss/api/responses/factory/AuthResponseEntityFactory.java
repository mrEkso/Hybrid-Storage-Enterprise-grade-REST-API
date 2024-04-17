package com.example.oss.api.responses.factory;

import com.example.oss.api.dto.UserDto;
import com.example.oss.api.responses.models.auth.LoginResponse;
import com.example.oss.api.responses.models.auth.LogoutResponse;
import com.example.oss.api.responses.models.auth.RegisterResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class AuthResponseEntityFactory {
    public static ResponseEntity<?> registerResponse(UserDto user, String token) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new RegisterResponse(user, token));
    }

    public static ResponseEntity<?> loginResponse(UserDto user, String token) {
        return ResponseEntity.ok(new LoginResponse(user, token));
    }

    public static ResponseEntity<?> logoutResponse() {
        return ResponseEntity.ok(new LogoutResponse());
    }
}
