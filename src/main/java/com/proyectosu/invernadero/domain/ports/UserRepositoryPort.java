package com.proyectosu.invernadero.domain.ports;

import com.proyectosu.invernadero.domain.model.auth.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepositoryPort {
    User save(User user);

    Optional<User> findByEmail(String email);

    Optional<User> findById(UUID id);

    boolean existsByEmail(String email);
}
