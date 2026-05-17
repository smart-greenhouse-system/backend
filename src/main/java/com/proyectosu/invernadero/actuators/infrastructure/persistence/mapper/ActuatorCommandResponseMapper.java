package com.proyectosu.invernadero.actuators.infrastructure.persistence.mapper;

import com.proyectosu.invernadero.actuators.domain.model.ActuatorEvent;
import com.proyectosu.invernadero.actuators.dto.response.ActuatorCommandResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ActuatorCommandResponseMapper {

    @Mapping(source = "deviceId", target = "device_id")
    ActuatorCommandResponse toResponse(ActuatorEvent actuatorEvent);
}