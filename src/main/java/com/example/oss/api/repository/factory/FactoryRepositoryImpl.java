package com.example.oss.api.repository.factory;

import com.example.oss.api.repository.PostEventRepository;
import com.example.oss.api.repository.PostRepository;
import com.example.oss.api.repository.UserRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Getter
public class FactoryRepositoryImpl implements FactoryRepository {
    UserRepository userRepository;
    PostRepository postRepository;
    PostEventRepository postEventRepository;

    @Autowired
    public FactoryRepositoryImpl(UserRepository userRepository, PostRepository postRepository, PostEventRepository postEventRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.postEventRepository = postEventRepository;
    }
}
