package com.proyectosu.invernadero.actuators.infrastructure.persistence.mapper;

import com.proyectosu.invernadero.actuators.domain.model.ActuatorEvent;
import com.proyectosu.invernadero.actuators.infrastructure.persistence.document.ActuatorEventDocument;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ActuatorEventMapper {

    @Mapping(target = "id", ignore = true)
    ActuatorEventDocument toDocument(ActuatorEvent domain);

    ActuatorEvent toDomain(ActuatorEventDocument document);
}