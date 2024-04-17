package com.example.oss.api.responses.models;

import lombok.Getter;

@Getter
public abstract class BaseAuthResponse extends BaseResponseWithData {
    private final String token;

    public BaseAuthResponse(int status, String msgCode, Object data, String token) {
        super(status, msgCode, data);
        this.token = token;
    }
}
