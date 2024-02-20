package com.example.phase1.api.services.User;

import com.example.phase1.api.dto.UserDto;
import com.example.phase1.api.models.User;
import com.example.phase1.api.services.modelMapperable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public interface UserService extends UserDetailsService, modelMapperable<User, UserDto> {
    User loadUserByUsername(String email);

    boolean checkPassword(User user, String password);

    User register(User user);
}
