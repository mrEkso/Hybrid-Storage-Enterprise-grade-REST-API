package com.example.oss.api.services;

public interface modelMappable<T, S> {
    S convertToDto(T t);
}
