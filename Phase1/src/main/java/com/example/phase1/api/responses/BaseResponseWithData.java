package com.example.phase1.api.responses;

import lombok.Data;
import lombok.Getter;

import static com.example.phase1.api.lang.LocalizationService.toLocale;

@Data
@Getter
public class BaseResponseWithData {
    private int status;
    private String message;
    private Object data;

    public BaseResponseWithData(int status, String msgCode, Object data) {
        this.status = status;
        this.message = toLocale(msgCode);
        this.data = data;
    }
}
