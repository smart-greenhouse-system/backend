package com.proyectosu.invernadero.domain.ports;

import com.proyectosu.invernadero.domain.model.auth.Role;

import java.util.Optional;

public interface RoleRepositoryPort {

    Optional<Role> findByName(String name);
}
