package com.proyectosu.invernadero.actuator.infrastructure.mappers;

import com.proyectosu.invernadero.actuator.application.command.ExecuteActuatorCommand;
import com.proyectosu.invernadero.actuator.dto.request.ExecuteActuatorRequest;
import com.proyectosu.invernadero.actuator.dto.response.ExecuteActuatorResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ActuatorDtoMapper {

    @Mapping(target = "deviceId", source = "deviceId")
    @Mapping(target = "actuator", source = "actuator")
    @Mapping(target = "action", source = "action")
    @Mapping(target = "origin", constant = "MANUAL")
    ExecuteActuatorCommand toCommand(ExecuteActuatorRequest request);

    default ExecuteActuatorResponse toResponse(ExecuteActuatorCommand command) {
        String normalizedAction = command.getAction() != null
                ? command.getAction().trim().toUpperCase()
                : null;

        return ExecuteActuatorResponse.builder()
                .message("Comando enviado correctamente")
                .deviceId(command.getDeviceId())
                .actuator(command.getActuator())
                .action(normalizedAction)
                .executed(true)
                .build();
    }
}
