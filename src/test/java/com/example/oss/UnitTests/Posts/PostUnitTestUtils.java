package com.example.oss.UnitTests.Posts;

import com.example.oss.api.models.Post;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static com.example.oss.UnitTests.Authorization.AuthorizationUnitTestUtils.createTestDBUser;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

public class PostUnitTestUtils {
    public static MockHttpServletRequestBuilder getPosts() {
        return get("/posts");
    }

    public static MockHttpServletRequestBuilder getPost(Post post) {
        return get("/posts/{id}", post.getId());
    }

    public static MockHttpServletRequestBuilder storePost(Post post) throws JsonProcessingException {
        return post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(post));
    }

    public static MockHttpServletRequestBuilder updatePost(Post post) throws JsonProcessingException {
        return put("/posts/{id}", post.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(post));
    }

    public static MockHttpServletRequestBuilder deletePost(Post post) {
        return delete("/posts/{id}", post.getId());
    }

    public static MockHttpServletRequestBuilder getMyPosts() {
        return get("/posts/my");
    }

    public static Post createTestPost() {
        return new Post("Test title", "Test subtitle");
    }

    public static Post createTestDBPost() {
        return new Post(createTestDBUser().getId(), "Test title", "Test subtitle");
    }
}
