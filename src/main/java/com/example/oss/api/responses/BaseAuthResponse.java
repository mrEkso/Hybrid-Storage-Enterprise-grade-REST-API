package com.example.oss.api.responses;

import lombok.Data;
import lombok.Getter;

import static com.example.oss.api.lang.LocalizationService.toLocale;

@Data
@Getter
public class BaseAuthResponse {
    private int status;
    private String message;
    private Object data;
    private String token;

    public BaseAuthResponse(int status, String msgCode, Object data, String token) {
        this.status = status;
        this.message = toLocale(msgCode);
        this.data = data;
        this.token = token;
    }
}
