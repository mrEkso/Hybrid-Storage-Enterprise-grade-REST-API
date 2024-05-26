package com.example.phase1.api.responses.models.auth;

import com.example.phase1.api.responses.models.BaseResponse;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class LogoutResponse extends BaseResponse {
    public LogoutResponse() {
        super(HttpStatus.OK.value(), "logout");
    }
}
