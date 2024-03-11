package com.example.phase1.api.responses.auth;

import com.example.phase1.api.responses.BaseResponse;
import org.springframework.http.HttpStatus;

public class LogoutResponse extends BaseResponse {
    public LogoutResponse() {
        super(HttpStatus.OK.value(), "logout");
    }
}
