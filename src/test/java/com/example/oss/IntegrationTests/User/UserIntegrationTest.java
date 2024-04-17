package com.example.oss.IntegrationTests.User;

import com.example.oss.api.models.User;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureDataMongo
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UserIntegrationTest {
    @Autowired
    private UserIntegrationTestUtils utils;

    @Autowired
    private MongoTemplate mongoTemplate;

    @AfterEach
    public void cleanUp() {
        mongoTemplate.getDb().listCollectionNames().forEach(collection -> mongoTemplate.dropCollection(collection));
    }

    @Test
    public void createUser_ShouldReturnCreated() throws Exception {
        utils.createUser(utils.createTestUser(), utils.createTestAdmin(), status().isCreated());
    }

    @Test
    public void getAllUsers_WithPagination_ShouldReturnPagedResults() throws Exception {
        int totalUsers = 10;
        for (int i = 0; i < totalUsers; i++) {
            utils.createUser(new User("user" + i + "@test.com", "password123"), utils.createTestAdmin(), status().isCreated());
        }
        int sizeOfPagination = 3;
        String content = utils.getAllUsers(1, sizeOfPagination, utils.createTestAdmin(), status().isOk()).getResponse().getContentAsString();
        int totalElements = JsonPath.read(content, "$.totalElements");
        assertThat(totalElements).isEqualTo(totalUsers);
        int contentLength = JsonPath.read(content, "$.content.length()");
        assertThat(contentLength).isEqualTo(sizeOfPagination);
    }

    @Test
    public void getUser_ShouldReturnOk() throws Exception {
        UUID userId = utils.getIDFromCreation(utils.createTestUser());
        utils.getUser(userId, utils.createTestAdmin(), status().isOk());
    }

    @Test
    public void updateUser_ShouldReturnOk() throws Exception {
        UUID userId = utils.getIDFromCreation(utils.createTestUser());
        utils.updateUser(userId, utils.createTestChangedUser(), utils.createTestAdmin(), status().isOk());
    }

    @Test
    public void deleteUser_ShouldReturnNoContent() throws Exception {
        UUID userId = utils.getIDFromCreation(utils.createTestUser());
        utils.deleteUser(userId, utils.createTestAdmin(), status().isNoContent());
    }
}










