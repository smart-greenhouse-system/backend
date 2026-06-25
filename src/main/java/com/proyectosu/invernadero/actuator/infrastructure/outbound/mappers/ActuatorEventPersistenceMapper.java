package com.proyectosu.invernadero.actuator.infrastructure.outbound.mappers;

import com.proyectosu.invernadero.actuator.domain.model.ActuatorEvent;
import com.proyectosu.invernadero.actuator.infrastructure.outbound.persistence.MongoActuatorEventDocument;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ActuatorEventPersistenceMapper {
    MongoActuatorEventDocument toDocument(ActuatorEvent actuatorEvent);

    default ActuatorEvent toDomain(MongoActuatorEventDocument document) {
        if (document == null) {
            return null;
        }

        return ActuatorEvent.restore(
                document.getId(),
                document.getDeviceId(),
                document.getActuator(),
                document.getAction(),
                Boolean.TRUE.equals(document.getExecuted()),
                document.getOrigin(),
                document.getEventType(),
                document.getStatus(),
                document.getTopic(),
                document.getTimeAction(),
                document.getCreatedAt()
        );
    }
}
