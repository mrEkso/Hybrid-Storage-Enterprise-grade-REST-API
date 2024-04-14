package com.example.oss.api.responses.crud;

import com.example.oss.api.responses.BaseResponseWithData;
import org.springframework.http.HttpStatus;

public class CreateResponse extends BaseResponseWithData {
    public CreateResponse(Object data) {
        super(HttpStatus.CREATED.value(), "create", data);
    }
}
