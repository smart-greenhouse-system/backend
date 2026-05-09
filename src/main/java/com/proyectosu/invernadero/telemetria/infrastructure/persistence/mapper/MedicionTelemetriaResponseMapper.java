package com.proyectosu.invernadero.telemetria.infrastructure.persistence.mapper;

import com.proyectosu.invernadero.telemetria.domain.model.MedicionTelemetria;
import com.proyectosu.invernadero.telemetria.dto.response.MedicionTelemetriaResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MedicionTelemetriaResponseMapper {

    @Mapping(target = "humedad_relativa", source = "humedadRelativa")
    @Mapping(target = "humedad_suelo", source = "humedadSuelo")
    MedicionTelemetriaResponse toResponse(MedicionTelemetria domain);
}