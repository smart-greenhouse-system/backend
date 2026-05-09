package com.proyectosu.invernadero.telemetria.infrastructure.persistence.mapper;

import com.proyectosu.invernadero.telemetria.domain.model.MedicionTelemetria;
import com.proyectosu.invernadero.telemetria.infrastructure.persistence.document.MedicionTelemetriaDocument;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MedicionTelemetriaMapper {

    MedicionTelemetria toDomain(MedicionTelemetriaDocument document);

    MedicionTelemetriaDocument toDocument(MedicionTelemetria domain);
}