package com.example.phase1.UnitTests.User;

import com.example.phase1.api.controllers.AuthorizationController;
import com.example.phase1.api.controllers.UserController;
import com.example.phase1.api.exceptions.handlers.AuthExceptionHandler;
import com.example.phase1.api.lang.LocalizationService;
import com.example.phase1.api.models.User;
import com.example.phase1.api.services.User.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.example.phase1.UnitTests.User.UserUnitTestUtils.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
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
