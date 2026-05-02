package com.proyectosu.invernadero.infraestructure.persistence.mapper;

import com.proyectosu.invernadero.domain.model.auth.Role;
import com.proyectosu.invernadero.infraestructure.entity.RoleEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    Role toDomain(RoleEntity entity);

    RoleEntity toEntity(Role role);
}
