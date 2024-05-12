package com.example.oss.api.config;

import com.azure.messaging.eventhubs.checkpointstore.blob.BlobCheckpointStore;
import com.azure.storage.blob.BlobContainerClientBuilder;
import com.example.oss.api.messaging.Post.PostEventConsumer;
import com.example.oss.api.models.PostEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StreamConfig {
    @Value("${spring.cloud.stream.container.connection-string}")
    private String connectionString;

    @Value("${spring.cloud.stream.bindings.input.destination}")
    private String inputDestination;

    @Bean
    public BlobCheckpointStore blobCheckpointStore() {
        return new BlobCheckpointStore(new BlobContainerClientBuilder()
                .connectionString(connectionString)
                .containerName(inputDestination)
                .buildAsyncClient());
    }

    @Bean
    public java.util.function.Consumer<PostEvent> postEventStreamConsumer(PostEventConsumer consumer) {
        return consumer;
    }
}