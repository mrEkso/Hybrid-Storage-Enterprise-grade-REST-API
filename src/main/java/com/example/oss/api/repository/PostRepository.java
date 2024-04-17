package com.example.oss.api.repository;

import com.example.oss.api.models.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

@org.springframework.stereotype.Repository
public interface PostRepository extends MongoRepository<Post, UUID> {

    Page<Post> findByUserId(Pageable pageable, UUID userId);

    Page<Post> findByTitle(Pageable pageable, String title);
}
