package com.proyectosu.invernadero.predictions.infrastructure.persistence.mapper;

import com.proyectosu.invernadero.predictions.domain.model.Prediccion;
import com.proyectosu.invernadero.predictions.infrastructure.persistence.document.PrediccionDocument;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PrediccionMapper {

    PrediccionDocument toDocument(Prediccion domain);

    Prediccion toDomain(PrediccionDocument document);
}