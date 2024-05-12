package com.example.oss.api.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@ToString
@NoArgsConstructor
@Table(name = "post_events")
public class PostEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    private UUID postId;

    @NotNull
    private UUID userId;

    private final Instant creationDate = Instant.now();

    public PostEvent(UUID postId, UUID userId) {
        this.postId = postId;
        this.userId = userId;
    }
}
