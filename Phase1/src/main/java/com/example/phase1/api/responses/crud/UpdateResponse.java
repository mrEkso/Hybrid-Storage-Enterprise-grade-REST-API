package com.example.phase1.api.responses.crud;

import com.example.phase1.api.responses.BaseResponseWithData;
import org.springframework.http.HttpStatus;

public class UpdateResponse extends BaseResponseWithData {
    public UpdateResponse(Object data) {
        super(HttpStatus.OK.value(), "update", data);
    }
}
