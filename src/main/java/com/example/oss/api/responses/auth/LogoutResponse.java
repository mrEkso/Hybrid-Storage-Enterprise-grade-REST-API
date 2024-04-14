package com.example.oss.api.responses.auth;

import com.example.oss.api.responses.BaseResponse;
import org.springframework.http.HttpStatus;

public class LogoutResponse extends BaseResponse {
    public LogoutResponse() {
        super(HttpStatus.OK.value(), "logout");
    }
}
