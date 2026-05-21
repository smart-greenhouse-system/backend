package com.proyectosu.invernadero.prediction.infrastructure.outbound.mappers;

import com.proyectosu.invernadero.prediction.domain.model.Prediction;
import com.proyectosu.invernadero.prediction.infrastructure.outbound.persistence.MongoPredictionDocument;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PredictionPersistenceMapper {
    MongoPredictionDocument toDocument(Prediction prediction);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "deviceId", source = "deviceId")
    @Mapping(target = "actuatorId", source = "actuatorId")
    @Mapping(target = "timeAction", source = "timeAction")
    @Mapping(target = "processed", source = "processed")
    @Mapping(target = "automaticMode", source = "automaticMode")
    @Mapping(target = "executed", source = "executed")
    @Mapping(target = "createdAt", source = "createdAt")
    default Prediction toDomain(MongoPredictionDocument document) {
        if (document == null) {
            return null;
        }

        return Prediction.restore(
                document.getId(),
                document.getDeviceId(),
                document.getActuatorId(),
                document.getTimeAction(),
                Boolean.TRUE.equals(document.getProcessed()),
                Boolean.TRUE.equals(document.getAutomaticMode()),
                Boolean.TRUE.equals(document.getExecuted()),
                document.getCreatedAt()
        );
    }
}
