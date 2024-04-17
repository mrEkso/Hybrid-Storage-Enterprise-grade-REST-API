package com.example.oss.api.responses.models.crud;

import com.example.oss.api.responses.models.BaseResponseWithData;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CreateResponse extends BaseResponseWithData {
    public CreateResponse(Object data) {
        super(HttpStatus.CREATED.value(), "create", data);
    }
}
