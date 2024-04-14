package com.example.oss.api.services.User;

import com.example.oss.api.dto.UserDto;
import com.example.oss.api.models.User;
import com.example.oss.api.services.modelMappable;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public interface UserService extends UserDetailsService, modelMappable<User, UserDto> {
    Page<User> findAll(int page, int size);

    User findById(UUID userId);

    User save(User user);

    User update(UUID userId, User user);

    void delete(UUID userId);

    User loadUserByUsername(String email);

    boolean checkPassword(User user, String password);
}
