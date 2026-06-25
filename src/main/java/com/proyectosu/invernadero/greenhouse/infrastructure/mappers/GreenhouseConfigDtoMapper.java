package com.proyectosu.invernadero.greenhouse.infrastructure.mappers;

import com.proyectosu.invernadero.greenhouse.application.command.UpdateGreenhouseConfigCommand;
import com.proyectosu.invernadero.greenhouse.domain.model.GreenhouseConfig;
import com.proyectosu.invernadero.greenhouse.dto.request.UpdateGreenhouseConfigRequest;
import com.proyectosu.invernadero.greenhouse.dto.response.GreenhouseConfigResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GreenhouseConfigDtoMapper {

    @Mapping(target = "greenhouseName", source = "nombreInvernadero")
    @Mapping(target = "automaticMode", source = "modoAutomatico")
    @Mapping(target = "aiAnalysisFrequencyMinutes", source = "frecuenciaAnalisisIaMin")
    UpdateGreenhouseConfigCommand toCommand(UpdateGreenhouseConfigRequest request);

    @Mapping(target = "nombreInvernadero", source = "greenhouseName")
    @Mapping(target = "modoAutomatico", source = "automaticMode")
    @Mapping(target = "frecuenciaAnalisisIaMin", source = "aiAnalysisFrequencyMinutes")
    GreenhouseConfigResponse toResponse(GreenhouseConfig greenhouseConfig);
}
