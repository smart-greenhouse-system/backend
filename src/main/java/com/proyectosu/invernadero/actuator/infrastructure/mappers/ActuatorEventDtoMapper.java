package com.proyectosu.invernadero.actuator.infrastructure.mappers;

import com.proyectosu.invernadero.actuator.domain.model.ActuatorEvent;
import com.proyectosu.invernadero.actuator.dto.response.ActuatorEventResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ActuatorEventDtoMapper {

    @Mapping(target = "executed", expression = "java(actuatorEvent.isExecuted())")
    ActuatorEventResponse toResponse(ActuatorEvent actuatorEvent);

    List<ActuatorEventResponse> toResponseList(List<ActuatorEvent> actuatorEvents);
}
