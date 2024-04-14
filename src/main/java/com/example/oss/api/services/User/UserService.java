package com.example.oss.api.services.User;

import com.example.oss.api.dto.UserDto;
import com.example.oss.api.models.User;
import com.example.oss.api.services.modelMappable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public interface UserService extends UserDetailsService, modelMappable<User, UserDto> {
    User loadUserByUsername(String email);

    boolean checkPassword(User user, String password);

    User register(User user);
}
