package com.example.oss.UnitTests.Authorization;

import com.example.oss.api.models.User;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.time.LocalDate;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class AuthorizationUnitTestUtils {
    public static MockHttpServletRequestBuilder registerUser(User user) throws Exception {
        return post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(user));
    }

    public static MockHttpServletRequestBuilder loginUser(User user) throws Exception {
        return post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(user));
    }

    public static User createTestUser() {
        return new User("test@test.com", "testtest");
    }

    public static User createTestDBUser() {
        return new User(UUID.randomUUID(), "test@test.com", "testuser",
                "token", LocalDate.now());
    }
}
