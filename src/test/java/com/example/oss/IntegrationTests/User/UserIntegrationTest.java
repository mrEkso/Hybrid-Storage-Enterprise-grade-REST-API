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
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UserIntegrationTest {
    @Autowired
    private UserIntegrationTestUtils utils;

    @Test
    @Transactional
    public void createUser_ShouldReturnCreated() throws Exception {
        utils.createUser(utils.createTestUser(), utils.createTestAdmin(), status().isCreated());
    }

    @Test
    @Transactional
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
    @Transactional
    public void getUser_ShouldReturnOk() throws Exception {
        UUID userId = utils.getIDFromCreation(utils.createTestUser());
        utils.getUser(userId, utils.createTestAdmin(), status().isOk());
    }

    @Test
    @Transactional
    public void updateUser_ShouldReturnOk() throws Exception {
        UUID userId = utils.getIDFromCreation(utils.createTestUser());
        utils.updateUser(userId, utils.createTestChangedUser(), utils.createTestAdmin(), status().isOk());
    }

    @Test
    @Transactional
    public void deleteUser_ShouldReturnNoContent() throws Exception {
        UUID userId = utils.getIDFromCreation(utils.createTestUser());
        utils.deleteUser(userId, utils.createTestAdmin(), status().isNoContent());
    }
}










