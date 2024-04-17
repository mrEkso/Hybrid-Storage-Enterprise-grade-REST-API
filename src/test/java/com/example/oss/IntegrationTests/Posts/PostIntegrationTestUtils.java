package com.example.oss.IntegrationTests.Posts;

import com.example.oss.api.models.Post;
import com.example.oss.api.models.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.UUID;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@Component
public class PostIntegrationTestUtils {
    private final ObjectMapper objectMapper;
    private final MockMvc mockMvc;

    @Autowired
    public PostIntegrationTestUtils(ObjectMapper objectMapper, MockMvc mockMvc) {
        this.objectMapper = objectMapper;
        this.mockMvc = mockMvc;
    }

    public void getPosts(ResultMatcher resultMatcher) throws Exception {
        mockMvc.perform(get("/posts"))
                .andExpect(resultMatcher);
    }

    public void getPost(UUID uuid, User user, ResultMatcher resultMatcher) throws Exception {
        mockMvc.perform(get("/posts/{id}", uuid).with(user(user)))
                .andExpect(resultMatcher);
    }

    public MvcResult storePost(Post post, User user, ResultMatcher resultMatcher) throws Exception {
        return mockMvc.perform(post("/posts").with(user(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(post)))
                .andExpect(resultMatcher)
                .andReturn();
    }

    public void updatePost(Post post, User user, ResultMatcher resultMatcher) throws Exception {
        mockMvc.perform(put("/posts/{id}", post.getId()).with(user(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(post)))
                .andExpect(resultMatcher);
    }

    public void deletePost(Post post, User user, ResultMatcher resultMatcher) throws Exception {
        mockMvc.perform(delete("/posts/{id}", post.getId()).with(user(user)))
                .andExpect(resultMatcher);
    }

    public void getMyPosts(User user, ResultMatcher resultMatcher) throws Exception {
        mockMvc.perform(get("/posts/my").with(user(user)))
                .andExpect(resultMatcher);
    }

    public User createTestUser() {
        return new User("test@test.com", "testuser");
    }

    public Post createTestPost() {
        return new Post("Test title", "Test subtitle");
    }
}
