package com.example.oss.api.controllers;

import com.example.oss.api.models.User;
import com.example.oss.api.services.User.UserService;
import jakarta.security.auth.message.AuthException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.oss.api.responses.factory.AuthResponseEntityFactory.*;


@RestController
@RequestMapping("/auth")
public class AuthorizationController {
    UserService userService;

    @Autowired
    public AuthorizationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @ResponseBody
    protected ResponseEntity<?> register(@Valid @RequestBody User user) throws AuthException {
        if (userService.loadUserByUsername(user.getEmail()) != null)
            throw new AuthException("error.register.email.exists");
        User dbUser = userService.save(user);
        return registerResponse(
                userService.convertToDto(dbUser),
                dbUser.getToken()
        );
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<?> login(@Valid @RequestBody User user) throws AuthException {
        User dbUser = userService.loadUserByUsername(user.getEmail());

        if (dbUser == null || userService.checkPassword(dbUser, user.getPassword()))
            throw new AuthException("error.login.failed");
        return loginResponse(
                userService.convertToDto(dbUser),
                dbUser.getToken()
        );
    }

    @GetMapping("/logout")
    @ResponseBody
    protected ResponseEntity<?> logout() {
        return logoutResponse();
    }
}
