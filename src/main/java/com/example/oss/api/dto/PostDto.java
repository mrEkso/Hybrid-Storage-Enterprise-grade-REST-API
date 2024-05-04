package com.example.oss.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
public class PostDto {
    private UUID id;
    private UUID userId;
    private String title;
    private String subtitle;
    private Instant updatedAt;
    private boolean open;
}
