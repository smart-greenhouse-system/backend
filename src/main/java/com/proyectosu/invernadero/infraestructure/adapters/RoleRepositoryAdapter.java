package com.proyectosu.invernadero.infraestructure.adapters;

import com.proyectosu.invernadero.domain.model.auth.Role;
import com.proyectosu.invernadero.domain.ports.RoleRepositoryPort;
import com.proyectosu.invernadero.infraestructure.persistence.mapper.RoleMapper;
import com.proyectosu.invernadero.infraestructure.repository.RoleJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RoleRepositoryAdapter implements RoleRepositoryPort {

    private final RoleJpaRepository roleJpaRepository;
    private final RoleMapper roleMapper;

    public RoleRepositoryAdapter(RoleJpaRepository roleJpaRepository,
                                 RoleMapper roleMapper) {
        this.roleJpaRepository = roleJpaRepository;
        this.roleMapper = roleMapper;
    }

    @Override
    public Optional<Role> findByName(String name) {
        return roleJpaRepository.findByName(name)
                .map(roleMapper::toDomain);
    }
}
