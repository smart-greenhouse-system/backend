package com.proyectosu.invernadero.auth.infraestructure.persistence.mapper;

import com.proyectosu.invernadero.auth.domain.model.Role;
import com.proyectosu.invernadero.auth.infraestructure.persistence.entity.RoleEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    Role toDomain(RoleEntity entity);

    RoleEntity toEntity(Role role);
}
