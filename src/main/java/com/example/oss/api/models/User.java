package com.example.oss.api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

@Document(collection = "users")
@Getter
@NoArgsConstructor
public class User implements UserDetails {
    @Id
    private UUID id = UUID.randomUUID();

    @NotBlank(message = "Адрес електронної пошти не може бути пустим")
    @Size(min = 4, max = 100)
    @Email
    @Indexed(unique = true)
    private String email;

    @NotBlank(message = "Пароль не може бути пустим")
    @Size(min = 6, max = 64)
    @Setter
    private String password;

    @Setter
    @JsonIgnore
    private String token;

    @Past(message = "День народження повинен бути в минулому")
    private LocalDate birthdate;

    public User(UUID id, String email, String password, String token, LocalDate birthdate) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.token = token;
        this.birthdate = birthdate;
    }

    public User(String email, String password, String token, LocalDate birthdate) {
        this.email = email;
        this.password = password;
        this.token = token;
        this.birthdate = birthdate;
    }

    public User(String email, String password, LocalDate birthdate) {
        this.email = email;
        this.password = password;
        this.birthdate = birthdate;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getUsername() {
        return this.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
