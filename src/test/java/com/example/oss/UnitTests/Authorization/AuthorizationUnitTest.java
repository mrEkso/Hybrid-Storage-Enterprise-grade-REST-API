package com.example.oss.UnitTests.Authorization;

import com.example.oss.api.controllers.AuthorizationController;
import com.example.oss.api.exceptions.handlers.AuthExceptionHandler;
import com.example.oss.api.lang.LocalizationService;
import com.example.oss.api.services.User.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.example.oss.UnitTests.Authorization.AuthorizationUnitTestUtils.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthorizationUnitTest {
    private MockMvc mockMvc;
    private final UserService userService = Mockito.mock(UserService.class);

    @BeforeEach
    void setUp() {
        configureLocale();
        mockMvc = MockMvcBuilders
                .standaloneSetup(new AuthorizationController(userService))
                .setControllerAdvice(new AuthExceptionHandler())
                .build();
    }

    private void configureLocale() {
        ResourceBundleMessageSource rs = new ResourceBundleMessageSource();
        rs.setBasenames("messages");
        rs.setDefaultEncoding("UTF-8");
        LocalizationService.setMessageSource(rs);
    }

    @Test
    void register_WhenUserDoesNotExist_ShouldReturnIsCreated() throws Exception {
        given(userService.save(any())).willReturn(createTestDBUser());
        mockMvc.perform(registerUser(createTestUser())).andExpect(status().isCreated());
    }

    @Test
    void register_WhenUserExists_ShouldReturnUnauthorized() throws Exception {
        given(userService.save(any())).willReturn(createTestDBUser());
        mockMvc.perform(registerUser(createTestUser())).andExpect(status().isCreated());

        given(userService.loadUserByUsername(any())).willReturn(createTestDBUser());
        mockMvc.perform(registerUser(createTestUser())).andExpect(status().isUnauthorized());
    }

    @Test
    void login_WhenUserExists_ShouldReturnOk() throws Exception {
        given(userService.loadUserByUsername(any())).willReturn(createTestDBUser());
        mockMvc.perform(loginUser(createTestUser())).andExpect(status().isOk());
    }

    @Test
    void login_WhenUserDoesNotExists_ShouldReturnUnauthorized() throws Exception {
        mockMvc.perform(loginUser(createTestUser())).andExpect(status().isUnauthorized());
    }
}
