package com.example.phase1.api.responses.crud;

import com.example.phase1.api.responses.BaseResponse;
import org.springframework.http.HttpStatus;

public class DeleteResponse extends BaseResponse {
    public DeleteResponse() {
        super(HttpStatus.NO_CONTENT.value(), "delete");
    }
}
