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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "email"
        })
})
public class User implements UserDetails {
    @Id
    @Setter
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "Адрес електронної пошти не може бути пустим")
    @Size(min = 4, max = 100)
    @Email
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

    public static final int ROLE_ADMIN = 1;
    public static final int ROLE_USER = 2;
    private int role = ROLE_USER;

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

    public User(String email, String password, int role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + (role == ROLE_ADMIN ? "ADMIN" : "USER")));
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
