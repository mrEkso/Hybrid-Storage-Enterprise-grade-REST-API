package com.example.oss.IntegrationTests.User;

import com.example.oss.api.models.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.UUID;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Component
public class UserIntegrationTestUtils {
    private final String URL = "/api/v1/users";

    private final ObjectMapper objectMapper;
    private final MockMvc mockMvc;

    @Autowired
    public UserIntegrationTestUtils(ObjectMapper objectMapper, MockMvc mockMvc) {
        this.objectMapper = objectMapper;
        this.mockMvc = mockMvc;
    }

    public MvcResult getAllUsers(int page, int size, User admin, ResultMatcher resultMatcher) throws Exception {
        return mockMvc.perform(get(URL + "?page=" + page + "&size=" + size).with(user(admin)))
                .andExpect(resultMatcher)
                .andReturn();
    }

    public void getUser(UUID userId, User admin, ResultMatcher resultMatcher) throws Exception {
        mockMvc.perform(get(URL + "/" + userId).with(user(admin)))
                .andExpect(resultMatcher);
    }

    public MvcResult createUser(User user, User admin, ResultMatcher resultMatcher) throws Exception {
        return mockMvc.perform(post(URL).with(user(admin))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(resultMatcher)
                .andReturn();
    }

    public void updateUser(UUID userId, User user, User admin, ResultMatcher resultMatcher) throws Exception {
        mockMvc.perform(put(URL + "/" + userId).with(user(admin))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(resultMatcher);
    }

    public void deleteUser(UUID userId, User admin, ResultMatcher resultMatcher) throws Exception {
        mockMvc.perform(delete(URL + "/" + userId).with(user(admin)))
                .andExpect(resultMatcher);
    }

    public UUID getIDFromCreation(User user) throws Exception {
        return UUID.fromString(JsonPath.parse(createUser(user, createTestAdmin(), status().isCreated())
                        .getResponse().getContentAsString())
                .read("$.data.id"));
    }

    public User createTestUser() {
        return new User("test@test.com", "testtest");
    }

    public User createTestChangedUser() {
        return new User("changed@test.com", "changedtest");
    }

    public User createTestAdmin() {
        return new User("test@test.com", "testtest", User.ROLE_ADMIN);
    }
}
