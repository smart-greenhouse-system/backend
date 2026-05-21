package com.proyectosu.invernadero.actuator.infrastructure.mappers;

import com.proyectosu.invernadero.actuator.application.command.CreateActuatorCommand;
import com.proyectosu.invernadero.actuator.application.command.UpdateActuatorCommand;
import com.proyectosu.invernadero.actuator.domain.model.Actuator;
import com.proyectosu.invernadero.actuator.dto.request.CreateActuatorRequest;
import com.proyectosu.invernadero.actuator.dto.request.UpdateActuatorRequest;
import com.proyectosu.invernadero.actuator.dto.response.ActuatorResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ActuatorManagementDtoMapper {

    CreateActuatorCommand toCreateCommand(CreateActuatorRequest request);

    @Mapping(target = "actuatorId", source = "actuatorId")
    UpdateActuatorCommand toUpdateCommand(String actuatorId, UpdateActuatorRequest request);

    @Mapping(target = "enabled", expression = "java(actuator.isEnabled())")
    ActuatorResponse toResponse(Actuator actuator);

    List<ActuatorResponse> toResponseList(List<Actuator> actuators);
}
