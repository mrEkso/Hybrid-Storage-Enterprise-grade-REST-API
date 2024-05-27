package com.example.oss.api.services.User;

import com.example.oss.api.dto.UserDto;
import com.example.oss.api.models.User;
import com.example.oss.api.repository.UserRepository;
import com.example.oss.api.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor_ = @Lazy)
public class UserServiceImpl implements UserService {
    private final int PAGE_SIZE = 3;

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final ModelMapper modelMapper;

    public Page<User> findAll(int page, int size) {
        if (size > 0) return userRepository.findAll(PageRequest.of(page, size));
        return userRepository.findAll(PageRequest.of(page, PAGE_SIZE));
    }

    public User findById(UUID userId) {
        return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User save(User user) {
        user.setPassword(encoder().encode(user.getPassword()));
        user.setToken(jwtTokenProvider.createToken(user));
        return userRepository.save(user);
    }

    public User update(UUID userId, User user) {
        user.setId(userId);
        return save(user);
    }

    public void delete(UUID userId) {
        userRepository.deleteById(userId);
    }

    @Override

    public User loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email);
    }

    @Override
    public boolean checkPassword(User user, String password) {
        return !encoder().matches(password, loadUserByUsername(user.getEmail()).getPassword());
    }

    public UserDto convertToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
