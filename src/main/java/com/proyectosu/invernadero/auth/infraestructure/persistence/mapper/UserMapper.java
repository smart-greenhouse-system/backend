package com.proyectosu.invernadero.auth.infraestructure.persistence.mapper;

import com.proyectosu.invernadero.auth.domain.model.User;
import com.proyectosu.invernadero.auth.domain.model.enums.UserStatus;
import com.proyectosu.invernadero.auth.infraestructure.persistence.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {RoleMapper.class})
public interface UserMapper {

    @Mapping(target = "status", expression = "java(mapStatus(entity.getStatus()))")
    User toDomain(UserEntity entity);

    @Mapping(target = "status", expression = "java(mapStatusToString(user.getStatus()))")
    UserEntity toEntity(User user);

    default UserStatus mapStatus(String status) {
        return UserStatus.valueOf(status);
    }

    default String mapStatusToString(UserStatus status) {
        return status.name();
    }
}
