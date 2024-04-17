package com.example.oss.IntegrationTests.Authorization;

import com.example.oss.api.models.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Component
public class AuthorizationIntegrationTestUtils {
    private final ObjectMapper objectMapper;
    private final MockMvc mockMvc;

    @Autowired
    public AuthorizationIntegrationTestUtils(ObjectMapper objectMapper, MockMvc mockMvc) {
        this.objectMapper = objectMapper;
        this.mockMvc = mockMvc;
    }

    public MvcResult registerUser(User user, ResultMatcher resultMatcher) throws Exception {
        return mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(resultMatcher)
                .andReturn();
    }

    public void loginUser(User user, ResultMatcher resultMatcher) throws Exception {
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(resultMatcher);
    }

    public void logoutUserWithToken(String token, ResultMatcher resultMatcher) throws Exception {
        mockMvc.perform(get("/auth/logout")
                        .header("Authorization", "Bearer " + token))
                .andExpect(resultMatcher);
    }

    public void logoutUserWithoutToken(ResultMatcher resultMatcher) throws Exception {
        mockMvc.perform(get("/auth/logout"))
                .andExpect(resultMatcher);
    }

    public String registerAndGetToken(User user) throws Exception {
        return JsonPath.parse(registerUser(user, status().isCreated())
                        .getResponse().getContentAsString())
                .read("$.token");
    }

    public User createTestUser() {
        return new User("test@test.com", "testuser");
    }
}
