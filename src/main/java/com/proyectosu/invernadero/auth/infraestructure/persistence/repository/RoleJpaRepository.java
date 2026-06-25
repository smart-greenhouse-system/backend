package com.proyectosu.invernadero.auth.infraestructure.persistence.repository;

import com.proyectosu.invernadero.auth.infraestructure.persistence.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleJpaRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByName(String name);
    boolean existsByName(String name);
}
