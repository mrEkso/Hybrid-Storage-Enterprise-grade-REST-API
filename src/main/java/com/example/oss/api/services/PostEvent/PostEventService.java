package com.example.oss.api.services.PostEvent;

import com.example.oss.api.models.PostEvent;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public interface PostEventService {
    Page<PostEvent> findAll(int page);

    PostEvent insert(PostEvent postEvent);
}
