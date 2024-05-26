package com.example.phase1.api.responses.models;

import lombok.Getter;

@Getter
public abstract class BaseResponseWithData extends BaseResponse {
    private final Object data;

    public BaseResponseWithData(int status, String msgCode, Object data) {
        super(status, msgCode);
        this.data = data;
    }
}
