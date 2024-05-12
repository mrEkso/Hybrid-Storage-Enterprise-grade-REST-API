package com.example.oss.api.messaging.Post;

import com.example.oss.api.messaging.EventPublisher;
import com.example.oss.api.models.PostEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class PostEventPublisher extends EventPublisher<PostEvent> {
    @Value("${spring.cloud.stream.bindings.output.destination}")
    private String bindingName;

    public PostEventPublisher(StreamBridge streamBridge) {
        super(streamBridge);
    }

    @Override
    public void publish(PostEvent event) {
        System.out.println(event);
        streamBridge.send(bindingName, MessageBuilder.withPayload(event).build());
    }
}