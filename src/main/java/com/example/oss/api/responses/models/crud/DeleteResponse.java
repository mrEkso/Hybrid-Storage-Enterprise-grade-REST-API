package com.example.oss.api.responses.models.crud;

import com.example.oss.api.responses.models.BaseResponse;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class DeleteResponse extends BaseResponse {
    public DeleteResponse() {
        super(HttpStatus.NO_CONTENT.value(), "delete");
    }
}
