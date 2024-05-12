package com.example.oss.api.messaging;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;

@RequiredArgsConstructor
public abstract class EventPublisher<T> {
    protected final StreamBridge streamBridge;

    public abstract void publish(T event);
}