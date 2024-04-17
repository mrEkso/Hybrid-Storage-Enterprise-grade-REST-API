package com.example.oss.api.responses.models;

import lombok.Getter;

import static com.example.oss.api.lang.LocalizationService.toLocale;

@Getter
public abstract class BaseResponse {
    private final int status;
    private final String message;

    public BaseResponse(int status, String msgCode) {
        this.status = status;
        this.message = toLocale(msgCode);
    }
}
