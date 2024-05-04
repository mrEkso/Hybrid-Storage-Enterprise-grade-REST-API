package com.example.oss.api.controllers;

import com.example.oss.api.dto.PostDto;
import com.example.oss.api.models.Post;
import com.example.oss.api.models.User;
import com.example.oss.api.services.Post.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.example.oss.api.exceptions.factory.ExceptionResponseFactory.forbidden;
import static com.example.oss.api.exceptions.factory.ExceptionResponseFactory.notFound;
import static com.example.oss.api.responses.factory.CrudResponseEntityFactory.*;

@RestController("postController")
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    final private PostService postService;

    @GetMapping({"", "/search"})
    @ResponseBody
    protected Page<PostDto> index(@RequestParam(required = false) String searchText,
                                  @RequestParam(defaultValue = "0") int page) {
        return postService.findAll(searchText, page).map(postService::convertToDto);
    }

    @GetMapping("/{id}")
    protected PostDto show(@PathVariable UUID id) {
        return postService.convertToDto(findPostById(id));
    }

    @PostMapping
    public ResponseEntity<?> store(@Valid @RequestBody Post post,
                                   @AuthenticationPrincipal User user) {
        return createResponse(postService.convertToDto(postService.insert(post, user)));
    }

    @PutMapping("/{id}")
    protected ResponseEntity<?> update(@PathVariable UUID id,
                                       @Valid @RequestBody Post post,
                                       @AuthenticationPrincipal User user) {
        checkOwnership(id, user);
        return updateResponse(postService.convertToDto(postService.update(post, user)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> destroy(@PathVariable UUID id,
                                     @AuthenticationPrincipal User user) {
        checkOwnership(id, user);
        postService.delete(findPostById(id));
        return deleteResponse();
    }

    @GetMapping("/my")
    protected Page<?> myPosts(@RequestParam(defaultValue = "0") int page,
                              @AuthenticationPrincipal User user) {
        return postService.findByUser(user, page).map(postService::convertToDto);
    }

    protected Post findPostById(UUID id) {
        return postService.findById(id).orElseThrow(() -> notFound("error.posts.not.found"));
    }

    protected void checkOwnership(UUID postId, User user) {
        if (postService.findByIdAndUserId(postId, user.getId()).isEmpty())
            throw forbidden("error.posts.forbidden");
    }
}
