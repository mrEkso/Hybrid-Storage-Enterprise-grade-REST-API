package com.example.phase1.api.services.User;

import com.example.phase1.api.dto.UserDto;
import com.example.phase1.api.models.User;
import com.example.phase1.api.services.modelMappable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public interface UserService extends UserDetailsService, modelMappable<User, UserDto> {
    List<User> findAll();

    User findById(UUID userId);

    User save(User user);

    User update(UUID userId, User user);

    void delete(UUID userId);

    User loadUserByUsername(String email);

    boolean checkPassword(User user, String password);
}
