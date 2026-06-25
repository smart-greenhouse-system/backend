package com.proyectosu.invernadero.actuator.infrastructure.outbound.mappers;

import com.proyectosu.invernadero.actuator.domain.model.Actuator;
import com.proyectosu.invernadero.actuator.infrastructure.outbound.persistence.MongoActuatorDocument;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ActuatorPersistenceMapper {

    MongoActuatorDocument toDocument(Actuator actuator);

    default Actuator toDomain(MongoActuatorDocument document) {
        if (document == null) {
            return null;
        }

        return Actuator.restore(
                document.getActuatorId(),
                document.getDeviceId(),
                document.getActuador(),
                document.getNombre(),
                document.getEstado(),
                Boolean.TRUE.equals(document.getEnabled()),
                document.getCreatedAt(),
                document.getUpdatedAt()
        );
    }
}
