package com.example.oss.api.repository.factory;

import com.example.oss.api.repository.PostRepository;
import com.example.oss.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FactoryRepositoryImpl implements FactoryRepository {
    UserRepository userRepository;
    PostRepository postRepository;

    @Autowired
    public FactoryRepositoryImpl(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @Override
    public UserRepository getUserRepository() {
        return userRepository;
    }

    @Override
    public PostRepository getPostRepository() {
        return postRepository;
    }
}
