package com.example.oss.UnitTests.User;


import com.example.oss.api.controllers.UserController;
import com.example.oss.api.exceptions.handlers.AuthExceptionHandler;
import com.example.oss.api.lang.LocalizationService;
import com.example.oss.api.services.User.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.example.oss.UnitTests.User.UserUnitTestUtils.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserUnitTest {
    private MockMvc mockMvc;
    private final UserService userService = Mockito.mock(UserService.class);

    @BeforeEach
    void setUp() {
        configureLocale();
        mockMvc = MockMvcBuilders
                .standaloneSetup(new UserController(userService))
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
    void createUser_ShouldReturnCreated() throws Exception {
        given(userService.save(any())).willReturn(createTestDBUser());
        mockMvc.perform(createUser(createTestUser())).andExpect(status().isCreated());
    }

    @Test
    void getUser_ShouldReturnOk() throws Exception {
        given(userService.findById(any())).willReturn(createTestDBUser());
        mockMvc.perform(getUser(createTestDBUser().getId())).andExpect(status().isOk());
    }

    @Test
    void updateUser_ShouldReturnOk() throws Exception {
        given(userService.update(any(), any())).willReturn(createTestDBUser());
        mockMvc.perform(updateUser(createTestDBUser().getId(), createTestUser())).andExpect(status().isOk());
    }

    @Test
    void deleteUser_ShouldReturnNoContent() throws Exception {
        Mockito.doNothing().when(userService).delete(any());
        mockMvc.perform(deleteUser(createTestDBUser().getId())).andExpect(status().isNoContent());
    }
}
