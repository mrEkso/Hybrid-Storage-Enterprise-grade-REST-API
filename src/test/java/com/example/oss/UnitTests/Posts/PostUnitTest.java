package com.example.oss.UnitTests.Posts;

import com.example.oss.api.controllers.PostController;
import com.example.oss.api.exceptions.handlers.AuthExceptionHandler;
import com.example.oss.api.lang.LocalizationService;
import com.example.oss.api.models.Post;
import com.example.oss.api.models.User;
import com.example.oss.api.services.Post.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Collections;
import java.util.Optional;

import static com.example.oss.UnitTests.Authorization.AuthorizationUnitTestUtils.createTestDBUser;
import static com.example.oss.UnitTests.Authorization.AuthorizationUnitTestUtils.createTestUser;
import static com.example.oss.UnitTests.Posts.PostUnitTestUtils.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PostUnitTest {
    private MockMvc mockMvc;
    private final PostService postService = Mockito.mock(PostService.class);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        configureLocale();
        mockMvc = MockMvcBuilders
                .standaloneSetup(new PostController(postService))
                .setControllerAdvice(new AuthExceptionHandler())
                .setCustomArgumentResolvers(new HandlerMethodArgumentResolver() {
                    @Override
                    public boolean supportsParameter(MethodParameter parameter) {
                        return parameter.getParameterType().equals(User.class);
                    }

                    @Override
                    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
                        return createTestDBUser();
                    }
                })
                .build();
    }

    private void configureLocale() {
        ResourceBundleMessageSource rs = new ResourceBundleMessageSource();
        rs.setBasenames("messages");
        rs.setDefaultEncoding("UTF-8");
        LocalizationService.setMessageSource(rs);
    }

    @Test
    void getPosts_WhenTheresNothing_ShouldReturnOk() throws Exception {
        Page<Post> posts = new PageImpl<>(Collections.emptyList(), PageRequest.of(0, 10), 0);
        given(postService.findAll(null, 0)).willReturn(posts);
        mockMvc.perform(getPosts()).andExpect(status().isOk());
    }

    @Test
    void getPost_WhenPostExists_ShouldReturnOk() throws Exception {
        Post testPost = createTestDBPost();
        given(postService.findById(testPost.getId())).willReturn(Optional.of(testPost));
        mockMvc.perform(getPost(testPost)).andExpect(status().isOk());
    }

    @Test
    void getPost_WhenPostDoesNotExists_ShouldReturnNotFound() throws Exception {
        Post testPost = createTestPost();
        given(postService.findById(testPost.getId())).willReturn(Optional.empty());
        mockMvc.perform(getPost(testPost)).andExpect(status().isNotFound());
    }

    @Test
    void storePost_WhenPostDoesNotExists_ShouldReturnOk() throws Exception {
        Post testPost = createTestPost();
        User testUser = createTestUser();
        testPost.setUserId(testUser.getId());
        given(postService.insert(testPost, testUser)).willReturn(testPost);
        mockMvc.perform(storePost(testPost)).andExpect(status().isCreated());
    }

    @Test
    void updatePost_WhenPostExistsAndUserIsNotOwner_ShouldReturnForbidden() throws Exception {
        Post testPost = createTestDBPost();
        given(postService.findById(testPost.getId())).willReturn(Optional.of(testPost));
        mockMvc.perform(updatePost(testPost))
                .andExpect(status().isForbidden());
    }

    @Test
    void updatePost_WhenPostDoesNotExist_ShouldReturnForbidden() throws Exception {
        Post testPost = createTestDBPost();
        given(postService.findById(testPost.getId())).willReturn(Optional.empty());
        mockMvc.perform(updatePost(testPost))
                .andExpect(status().isForbidden());
    }

    @Test
    void deletePost_WhenPostExistsAndUserIsNotOwner_ShouldReturnForbidden() throws Exception {
        Post testPost = createTestDBPost();
        given(postService.findById(testPost.getId())).willReturn(Optional.of(testPost));
        mockMvc.perform(deletePost(testPost))
                .andExpect(status().isForbidden());
    }

    @Test
    void getMyPosts_WhenPostsDoesNotExist_ShouldReturnOk() throws Exception {
        Page<Post> posts = new PageImpl<>(Collections.emptyList(), PageRequest.of(0, 10), 0);
        given(postService.findByUser(any(), anyInt())).willReturn(posts);
        mockMvc.perform(getMyPosts())
                .andExpect(status().isOk());
    }
}
