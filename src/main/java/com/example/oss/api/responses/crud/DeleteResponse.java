package com.example.oss.api.responses.crud;

import com.example.oss.api.responses.BaseResponse;
import org.springframework.http.HttpStatus;

public class DeleteResponse extends BaseResponse {
    public DeleteResponse() {
        super(HttpStatus.NO_CONTENT.value(), "delete");
    }
}
