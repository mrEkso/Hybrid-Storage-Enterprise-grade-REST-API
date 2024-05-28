package com.example.oss.api.services.Post;

import com.example.oss.api.dto.PostDto;
import com.example.oss.api.models.Post;
import com.example.oss.api.models.User;
import com.example.oss.api.repository.factory.FactoryRepository;
import com.example.oss.api.services.Blob.BlobStorageService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final int PAGE_SIZE = 3;

    private final FactoryRepository fr;
    private final BlobStorageService blobStorageService;
    private final ModelMapper modelMapper;

    @Override
    public Optional<Post> findById(UUID id) {
        return fr.getPostRepository().findById(id);
    }

    @Override
    public Optional<Post> findByIdAndUserId(UUID id, UUID userId) {
        if (blobStorageService.checkBlobExists(getBlobNameFromIdAndUserId(id, userId)))
            return fr.getPostRepository().findByIdAndUserId(id, userId);
        return Optional.empty();
    }

    @Override
    public Page<Post> findByUser(User user, int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        return fr.getPostRepository().findByUserId(pageable, user.getId());
    }

    @Override
    public Page<Post> findAll(String searchText, int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        if (searchText == null || searchText.isEmpty())
            return fr.getPostRepository().findAll(pageable);
        return fr.getPostRepository().findByTitle(pageable, searchText);
    }

    @Override
    public Post insert(Post post, User user) {
        post.setUserId(user.getId());
        blobStorageService.uploadBlob(getBlobNameFromModel(post));
        return fr.getPostRepository().save(post);
    }

    @Override
    public Post update(Post post, User user) {
        post.setUserId(user.getId());
        return fr.getPostRepository().save(post);
    }

    @Override
    public void delete(Post post) {
        blobStorageService.deleteBlob(getBlobNameFromModel(post));
        fr.getPostRepository().delete(post);
    }

    @Override
    public PostDto convertToDto(Post post) {
        return modelMapper.map(post, PostDto.class);
    }

    private String getBlobNameFromModel(Post post) {
        return getBlobNameFromIdAndUserId(post.getId(), post.getUserId());
    }

    private String getBlobNameFromIdAndUserId(UUID id, UUID userId) {
        String sanitizedId = id.toString().replaceAll("[^a-zA-Z0-9-]", "");
        String sanitizedUserId = userId.toString().replaceAll("[^a-zA-Z0-9-]", "");
        return sanitizedId + "_" + sanitizedUserId;
    }
}
