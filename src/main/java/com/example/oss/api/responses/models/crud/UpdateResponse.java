package com.example.oss.api.responses.models.crud;

import com.example.oss.api.responses.models.BaseResponseWithData;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UpdateResponse extends BaseResponseWithData {
    public UpdateResponse(Object data) {
        super(HttpStatus.OK.value(), "update", data);
    }
}
