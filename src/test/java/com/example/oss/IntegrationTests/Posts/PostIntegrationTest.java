package com.example.oss.IntegrationTests.Posts;

import com.example.oss.api.models.Post;
import com.example.oss.api.models.PostEvent;
import com.example.oss.api.models.User;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MvcResult;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureDataMongo
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PostIntegrationTest {
    private final PostIntegrationTestUtils utils;
    private final MongoTemplate mongoTemplate;
    @MockBean
    private StreamBridge streamBridge;

    @Value("${spring.cloud.stream.bindings.input.destination}")
    private String containerName;

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
    public void storePost_ShouldReturnCreated_AndSendCorrectPostEvent() throws Exception {
        User testUser = utils.createTestDBUser();
        Post testPost = utils.createTestPost();
        MvcResult result = utils.storePost(testPost, testUser, status().isCreated());
        String responseString = result.getResponse().getContentAsString();
        UUID createdPostId = UUID.fromString(JsonPath.parse(responseString).read("$.data.id", String.class));
        PostEvent expectedEvent = new PostEvent(createdPostId, testUser.getId());
        Mockito.verify(streamBridge).send(
                eq(containerName),
                argThat(argument -> argument instanceof GenericMessage &&
                        ((GenericMessage<?>) argument).getPayload() instanceof PostEvent &&
                        ((PostEvent) ((GenericMessage<?>) argument).getPayload()).getPostId().equals(expectedEvent.getPostId()) &&
                        ((PostEvent) ((GenericMessage<?>) argument).getPayload()).getUserId().equals(expectedEvent.getUserId()) &&
                        ((PostEvent) ((GenericMessage<?>) argument).getPayload()).getId() == null)
        );
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
        utils.deletePost(testPost, testUser, status().isOk());
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
