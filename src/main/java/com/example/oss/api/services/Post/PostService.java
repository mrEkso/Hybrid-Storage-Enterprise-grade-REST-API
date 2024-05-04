package com.example.oss.api.services.Post;

import com.example.oss.api.dto.PostDto;
import com.example.oss.api.models.Post;
import com.example.oss.api.models.User;
import com.example.oss.api.services.modelMappable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public interface PostService extends modelMappable<Post, PostDto> {
    Optional<Post> findById(UUID id);

    Optional<Post> findByIdAndUserId(UUID id, UUID UserId);

    Page<Post> findByUser(User user, int page);

    Page<Post> findAll(String searchText, int page);

    Post insert(Post post, User user);

    Post update(Post post, User user);

    void delete(Post post);
}
