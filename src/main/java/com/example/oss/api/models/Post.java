package com.example.oss.api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.UUID;

@Document(collection = "posts")
@Data
@NoArgsConstructor
public class Post {
    @Id
    private UUID id = UUID.randomUUID();

    @Setter
    private UUID userId;

    @NotBlank
    @Size(min = 5, max = 100)
    private String title;

    @NotBlank
    @Size(min = 5, max = 500)
    private String subtitle;

    @LastModifiedDate
    @JsonIgnore
    private Instant updatedAt;

    @Setter
    private boolean open;

    public Post(UUID id, UUID userId, String title, String subtitle) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.subtitle = subtitle;
    }

    public Post(UUID userId, String title, String subtitle) {
        this.userId = userId;
        this.title = title;
        this.subtitle = subtitle;
    }

    public Post(String title, String subtitle) {
        this.title = title;
        this.subtitle = subtitle;
    }
}
