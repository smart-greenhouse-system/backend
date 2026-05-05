package com.proyectosu.invernadero.auth.domain.ports;

import com.proyectosu.invernadero.auth.domain.model.Role;

import java.util.Optional;

public interface RoleRepositoryPort {

    Optional<Role> findByName(String name);
}
