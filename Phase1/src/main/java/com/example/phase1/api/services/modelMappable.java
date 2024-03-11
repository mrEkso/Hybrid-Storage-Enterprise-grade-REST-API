package com.example.phase1.api.services;

public interface modelMappable<T, S> {
    S convertToDto(T t);
}
