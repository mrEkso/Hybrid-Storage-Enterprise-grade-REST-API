package com.example.oss.api.repository;

import com.example.oss.api.models.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PostRepository extends MongoRepository<Post, UUID> {
    Optional<Post> findByIdAndUserId(UUID id, UUID userId);

    Page<Post> findByUserId(Pageable pageable, UUID userId);

    Page<Post> findByTitle(Pageable pageable, String title);
}
