package com.example.oss.api.exceptions.factory;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static com.example.oss.api.lang.LocalizationService.toLocale;

public class ExceptionResponseFactory {
    public static ResponseStatusException notFound(String message) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, toLocale(message));
    }

    public static ResponseStatusException forbidden(String message) {
        return new ResponseStatusException(HttpStatus.FORBIDDEN, toLocale(message));
    }

    public static ResponseStatusException badRequest(String message) {
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, toLocale(message));
    }
}
