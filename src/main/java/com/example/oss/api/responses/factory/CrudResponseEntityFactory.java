package com.example.oss.api.responses.factory;

import com.example.oss.api.responses.models.crud.CreateResponse;
import com.example.oss.api.responses.models.crud.DeleteResponse;
import com.example.oss.api.responses.models.crud.UpdateResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CrudResponseEntityFactory {
    public static <T> ResponseEntity<?> createResponse(T data) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new CreateResponse(data));
    }

    public static <T> ResponseEntity<?> updateResponse(T data) {
        return ResponseEntity.status(HttpStatus.OK).body(new UpdateResponse(data));
    }

    public static ResponseEntity<?> deleteResponse() {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new DeleteResponse());
    }
}
