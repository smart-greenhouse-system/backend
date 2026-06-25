package com.proyectosu.invernadero.device.infrastructure.outbound.mappers;

import com.proyectosu.invernadero.device.domain.model.Device;
import com.proyectosu.invernadero.device.infrastructure.outbound.persistence.MongoDeviceDocument;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DevicePersistenceMapper {

    MongoDeviceDocument toDocument(Device device);

    default Device toDomain(MongoDeviceDocument document) {
        if (document == null) {
            return null;
        }

        return Device.restore(
                document.getDeviceId(),
                document.getNombre(),
                document.getTipo(),
                document.getEstado(),
                document.getSensores(),
                document.getActuadores(),
                document.getLastSeen(),
                document.getCreatedAt()
        );
    }
}
