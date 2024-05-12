package com.example.oss.api.messaging.Post;

import com.example.oss.api.messaging.EventConsumer;
import com.example.oss.api.models.PostEvent;
import com.example.oss.api.services.PostEvent.PostEventService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostEventConsumer extends EventConsumer<PostEvent> {
    private final PostEventService postEventService;
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Override
    public void accept(PostEvent event) {
        System.out.println("Deserialized event: " + event);
        postEventService.insert(event);
    }
}