package com.example.oss.IntegrationTests.Authorization;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AuthorizationIntegrationTest {
    @Autowired
    private AuthorizationIntegrationTestUtils utils;

    @Test
    @Transactional
    public void register_WhenUserDoesNotExist_ShouldReturnIsCreated() throws Exception {
        utils.registerUser(utils.createTestUser(), status().isCreated());
    }

    @Test
    @Transactional
    public void register_WhenUserExists_ShouldReturnUnauthorized() throws Exception {
        utils.registerUser(utils.createTestUser(), status().isCreated());
        utils.registerUser(utils.createTestUser(), status().isUnauthorized());
    }

    @Test
    @Transactional
    public void login_WhenUserExists_ShouldReturnOk() throws Exception {
        utils.registerUser(utils.createTestUser(), status().isCreated());
        utils.loginUser(utils.createTestUser(), status().isOk());
    }

    @Test
    @Transactional
    public void login_WhenUserDoesNotExists_ShouldReturnUnauthorized() throws Exception {
        utils.loginUser(utils.createTestUser(), status().isUnauthorized());
    }

    @Test
    @Transactional
    public void logout_WhenUserIsAuthorized_ShouldReturnOk() throws Exception {
        String token = utils.registerAndGetToken(utils.createTestUser());
        utils.logoutUserWithToken(token, status().isOk());
    }

    @Test
    @Transactional
    public void logout_WhenUserIsNotAuthorized_ShouldReturnUnauthorized() throws Exception {
        utils.logoutUserWithoutToken(status().isUnauthorized());
    }
}