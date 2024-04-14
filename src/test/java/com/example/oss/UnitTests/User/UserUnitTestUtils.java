package com.example.oss.UnitTests.User;

import com.example.oss.api.models.User;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.time.LocalDate;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

public class UserUnitTestUtils {
    private static final String URL = "/api/v1/users";

    public static MockHttpServletRequestBuilder createUser(User user) throws Exception {
        return post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(user));
    }

    public static MockHttpServletRequestBuilder getUser(UUID userId) {
        return get(URL + "/" + userId);
    }

    public static MockHttpServletRequestBuilder updateUser(UUID userId, User user) throws Exception {
        return put(URL + "/" + userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(user));
    }

    public static MockHttpServletRequestBuilder deleteUser(UUID userId) {
        return delete(URL + "/" + userId);
    }

    public static User createTestUser() {
        return new User("test@test.com", "testtest");
    }

    public static User createTestDBUser() {
        return new User(UUID.randomUUID(), "test@test.com", "testtest",
                "token", LocalDate.now());
    }
}
