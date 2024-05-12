package com.example.oss.api.controllers;

import com.example.oss.api.models.PostEvent;
import com.example.oss.api.services.PostEvent.PostEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController("postEventController")
@RequestMapping("/post-events")
@RequiredArgsConstructor
public class PostEventController {
    final private PostEventService postEventService;

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping({""})
    @ResponseBody
    protected Page<PostEvent> index(@RequestParam(defaultValue = "0") int page) {
        return postEventService.findAll(page);
    }
}
