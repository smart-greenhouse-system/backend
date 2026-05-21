package com.proyectosu.invernadero.sensor.infrastructure.outbound.mappers;

import com.proyectosu.invernadero.sensor.domain.model.SensorData;
import com.proyectosu.invernadero.sensor.infrastructure.outbound.persistence.MongoSensorDataDocument;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SensorDataPersistenceMapper {

    MongoSensorDataDocument toDocument(SensorData sensorData);

    default SensorData toDomain(MongoSensorDataDocument document) {
        if (document == null) {
            return null;
        }

        return SensorData.restore(
                document.getId(),
                document.getDeviceId(),
                document.getSensores(),
                document.getCreatedAt()
        );
    }
}
