package com.example.oss.IntegrationTests.Autorization;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureDataMongo
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AuthorizationIntegrationTest {
    @Autowired
    private AuthorizationIntegrationTestUtils utils;

    @Autowired
    private MongoTemplate mongoTemplate;

    @AfterEach
    public void cleanUp() {
        mongoTemplate.getDb().listCollectionNames().forEach(collection -> mongoTemplate.dropCollection(collection));
    }

    @Test
    public void register_WhenUserDoesNotExist_ShouldReturnIsCreated() throws Exception {
        utils.registerUser(utils.createTestUser(), status().isCreated());
    }

    @Test
    public void register_WhenUserExists_ShouldReturnUnauthorized() throws Exception {
        utils.registerUser(utils.createTestUser(), status().isCreated());
        utils.registerUser(utils.createTestUser(), status().isUnauthorized());
    }

    @Test
    public void login_WhenUserExists_ShouldReturnOk() throws Exception {
        utils.registerUser(utils.createTestUser(), status().isCreated());
        utils.loginUser(utils.createTestUser(), status().isOk());
    }

    @Test
    public void login_WhenUserDoesNotExists_ShouldReturnUnauthorized() throws Exception {
        utils.loginUser(utils.createTestUser(), status().isUnauthorized());
    }

    @Test
    public void logout_WhenUserIsAuthorized_ShouldReturnOk() throws Exception {
        String token = utils.registerAndGetToken(utils.createTestUser());
        utils.logoutUserWithToken(token, status().isOk());
    }

    @Test
    public void logout_WhenUserIsNotAuthorized_ShouldReturnUnauthorized() throws Exception {
        utils.logoutUserWithoutToken(status().isUnauthorized());
    }
}
