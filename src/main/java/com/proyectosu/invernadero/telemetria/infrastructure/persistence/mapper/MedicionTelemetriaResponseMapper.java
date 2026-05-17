package com.proyectosu.invernadero.telemetria.infrastructure.persistence.mapper;

import com.proyectosu.invernadero.telemetria.domain.model.MedicionTelemetria;
import com.proyectosu.invernadero.telemetria.dto.response.MedicionTelemetriaResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MedicionTelemetriaResponseMapper {

    MedicionTelemetriaResponse toResponse(MedicionTelemetria domain);
}