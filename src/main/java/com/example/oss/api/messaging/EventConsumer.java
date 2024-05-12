package com.example.oss.api.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.function.Consumer;

public abstract class EventConsumer<T> implements Consumer<T> {
    @Override
    public abstract void accept(T event);
}