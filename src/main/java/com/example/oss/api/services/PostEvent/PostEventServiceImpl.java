package com.example.oss.api.services.PostEvent;


import com.example.oss.api.models.PostEvent;
import com.example.oss.api.repository.factory.FactoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostEventServiceImpl implements PostEventService {
    private final FactoryRepository fr;

    private final int PAGE_SIZE = 3;

    @Override
    public Page<PostEvent> findAll(int page) {
        return fr.getPostEventRepository().findAll(PageRequest.of(page, PAGE_SIZE));
    }

    @Override
    public PostEvent insert(PostEvent postEvent) {
        return fr.getPostEventRepository().save(postEvent);
    }
}
