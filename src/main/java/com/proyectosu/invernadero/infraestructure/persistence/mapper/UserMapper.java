package com.proyectosu.invernadero.infraestructure.persistence.mapper;

import com.proyectosu.invernadero.domain.model.auth.User;
import com.proyectosu.invernadero.domain.model.auth.enums.UserStatus;
import com.proyectosu.invernadero.infraestructure.entity.UserEntity;
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
