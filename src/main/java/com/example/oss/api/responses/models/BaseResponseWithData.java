package com.example.oss.api.responses.models;

import lombok.Getter;

@Getter
public abstract class BaseResponseWithData extends BaseResponse {
    private final Object data;

    public BaseResponseWithData(int status, String msgCode, Object data) {
        super(status, msgCode);
        this.data = data;
    }
}
