package com.example.phase1.api.responses;

import lombok.Data;
import lombok.Getter;

import static com.example.phase1.api.lang.LocalizationService.toLocale;

@Data
@Getter
public class BaseResponse {
    private int status;
    private String message;

    public BaseResponse(int status, String msgCode) {
        this.status = status;
        this.message = toLocale(msgCode);
    }
}
