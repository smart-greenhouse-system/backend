package com.proyectosu.invernadero.greenhouse.infrastructure.outbound.mappers;

import com.proyectosu.invernadero.greenhouse.domain.model.GreenhouseConfig;
import com.proyectosu.invernadero.greenhouse.infrastructure.outbound.persistence.MongoGreenhouseConfigDocument;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GreenhouseConfigPersistenceMapper {

    MongoGreenhouseConfigDocument toDocument(GreenhouseConfig greenhouseConfig);

    default GreenhouseConfig toDomain(MongoGreenhouseConfigDocument document) {
        if (document == null) {
            return null;
        }

        return GreenhouseConfig.restore(
                document.getId(),
                document.getGreenhouseName(),
                Boolean.TRUE.equals(document.getAutomaticMode()),
                document.getAiAnalysisFrequencyMinutes(),
                document.getCreatedAt()
        );
    }
}
