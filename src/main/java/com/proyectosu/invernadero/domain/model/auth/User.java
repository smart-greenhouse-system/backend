package com.proyectosu.invernadero.domain.model.auth;

import com.proyectosu.invernadero.domain.model.auth.enums.UserStatus;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Getter
public class User {

    private UUID id;
    private String email;
    private String password;
    private UserStatus status;
    private LocalDateTime createdAt;
    private Set<Role> roles;

    public User(UUID id, String email, String password, UserStatus status, LocalDateTime createdAt, Set<Role> roles) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.status = status;
        this.createdAt = createdAt;
        this.roles = roles;
    }

    public boolean isActive() {
        return this.status == UserStatus.ACTIVE;
    }

    public static boolean esPasswordValida(String password) {
        if (password == null) return false;
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";
        return password.matches(regex);
    }
}
