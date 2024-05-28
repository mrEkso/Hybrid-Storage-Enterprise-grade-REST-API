package com.example.oss.api.controllers;

import com.example.oss.api.models.User;
import com.example.oss.api.services.User.UserService;
import jakarta.security.auth.message.AuthException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.example.oss.api.responses.factory.CrudResponseEntityFactory.*;


@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Page<User> index(@RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "0") int size) {
        return userService.findAll(page, size);
    }

    @GetMapping("/{userId}")
    public User show(@PathVariable UUID userId) {
        return userService.findById(userId);
    }

    @PostMapping
    public ResponseEntity<?> store(@Valid @RequestBody User user) throws AuthException {
        if (userService.loadUserByUsername(user.getEmail()) != null)
            throw new AuthException("error.register.email.exists");
        return createResponse(userService.save(user));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> update(@PathVariable UUID userId, @Valid @RequestBody User user) {
        return updateResponse(userService.update(userId, user));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> destroy(@PathVariable UUID userId) {
        userService.delete(userId);
        return deleteResponse();
    }
}