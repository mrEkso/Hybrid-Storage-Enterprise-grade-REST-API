package com.example.oss.IntegrationTests.Posts;

import com.example.oss.api.models.Post;
import com.example.oss.api.models.User;
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
class PostIntegrationTest {
    private final PostIntegrationTestUtils utils;
    private final MongoTemplate mongoTemplate;

    @Autowired
    public PostIntegrationTest(PostIntegrationTestUtils utils, MongoTemplate mongoTemplate) {
        this.utils = utils;
        this.mongoTemplate = mongoTemplate;
    }

    @AfterEach
    public void cleanUp() {
        mongoTemplate.getDb().listCollectionNames().forEach(mongoTemplate::dropCollection);
    }

    @Test
    public void getPosts_ShouldReturnOk() throws Exception {
        utils.getPosts(status().isOk());
    }

    @Test
    public void getPost_WhenPostExists_ShouldReturnOk() throws Exception {
        User testUser = utils.createTestUser();
        Post testPost = utils.createTestPost();
        utils.storePost(testPost, testUser, status().isCreated());
        utils.getPost(testPost.getId(), testUser, status().isOk());
    }

    @Test
    public void storePost_ShouldReturnCreated() throws Exception {
        User testUser = utils.createTestUser();
        Post testPost = utils.createTestPost();
        utils.storePost(testPost, testUser, status().isCreated());
    }

    @Test
    public void updatePost_WhenPostExists_ShouldReturnOk() throws Exception {
        User testUser = utils.createTestUser();
        Post testPost = utils.createTestPost();
        utils.storePost(testPost, testUser, status().isCreated());
        testPost.setTitle("Updated title");
        utils.updatePost(testPost, testUser, status().isOk());
    }

    @Test
    public void deletePost_WhenPostExists_ShouldReturnOk() throws Exception {
        User testUser = utils.createTestUser();
        Post testPost = utils.createTestPost();
        utils.storePost(testPost, testUser, status().isCreated());
        utils.deletePost(testPost, testUser, status().isNoContent());
    }

    @Test
    public void myPosts_WhenUserHasPosts_ShouldReturnOk() throws Exception {
        User testUser = utils.createTestUser();
        Post testPost1 = utils.createTestPost();
        Post testPost2 = utils.createTestPost();
        utils.storePost(testPost1, testUser, status().isCreated());
        utils.storePost(testPost2, testUser, status().isCreated());
        utils.getMyPosts(testUser, status().isOk());
    }

    @Test
    public void myPosts_WhenUserHasNoPosts_ShouldReturnOkButEmpty() throws Exception {
        User testUser = utils.createTestUser();
        utils.getMyPosts(testUser, status().isOk());
    }
}
