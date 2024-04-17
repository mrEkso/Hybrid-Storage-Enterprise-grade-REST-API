package com.example.oss.api.responses.models.auth;

import com.example.oss.api.responses.models.BaseResponse;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class LogoutResponse extends BaseResponse {
    public LogoutResponse() {
        super(HttpStatus.OK.value(), "logout");
    }
}
