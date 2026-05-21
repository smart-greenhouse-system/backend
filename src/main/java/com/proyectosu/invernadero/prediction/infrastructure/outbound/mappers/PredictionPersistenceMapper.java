package com.proyectosu.invernadero.prediction.infrastructure.outbound.mappers;

import com.proyectosu.invernadero.prediction.domain.model.Prediction;
import com.proyectosu.invernadero.prediction.infrastructure.outbound.persistence.MongoPredictionDocument;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PredictionPersistenceMapper {

    MongoPredictionDocument toDocument(Prediction prediction);

    default Prediction toDomain(MongoPredictionDocument document) {
        if (document == null) {
            return null;
        }

        return Prediction.restore(
                document.getId(),
                document.getTipo(),
                document.getDeviceId(),
                document.getActuatorId(),
                document.getTimeAction(),
                Boolean.TRUE.equals(document.getProcessed()),
                Boolean.TRUE.equals(document.getAutomaticMode()),
                Boolean.TRUE.equals(document.getExecuted()),
                document.getCultivo(),
                document.getSuccess(),
                document.getEstadoPlanta(),
                document.getConfianza(),
                document.getTiempoCosechaDias(),
                document.getCreatedAt()
        );
    }
}
