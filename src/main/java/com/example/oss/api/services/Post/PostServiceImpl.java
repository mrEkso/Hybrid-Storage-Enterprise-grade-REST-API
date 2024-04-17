package com.example.oss.api.services.Post;

import com.example.oss.api.dto.PostDto;
import com.example.oss.api.models.Post;
import com.example.oss.api.models.User;
import com.example.oss.api.repository.factory.FactoryRepository;
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
    private final ModelMapper modelMapper;

    @Override
    public Optional<Post> findById(UUID id) {
        return fr.getPostRepository().findById(id);
    }

    @Override
    public Page<Post> findByUser(User user, int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        return fr.getPostRepository().findByUserId(pageable, user.getId());
    }

    @Override
    public Page<Post> findAll(String searchText, int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        if (searchText == null || searchText.equals(""))
            return fr.getPostRepository().findAll(pageable);
        return fr.getPostRepository().findByTitle(pageable, searchText);
    }

    @Override
    public Post insert(Post post, User user) {
        post.setUserId(user.getId());
        return fr.getPostRepository().save(post);
    }

    @Override
    public Post update(Post post) {
        return fr.getPostRepository().save(post);
    }

    @Override
    public void delete(Post post) {
        fr.getPostRepository().delete(post);
    }

    @Override
    public PostDto convertToDto(Post post) {
        return modelMapper.map(post, PostDto.class);
    }
}
