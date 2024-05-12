package com.example.oss.api.repository.factory;

import com.example.oss.api.repository.PostEventRepository;
import com.example.oss.api.repository.PostRepository;
import com.example.oss.api.repository.UserRepository;

public interface FactoryRepository {
    UserRepository getUserRepository();

    PostRepository getPostRepository();

    PostEventRepository getPostEventRepository();
}
