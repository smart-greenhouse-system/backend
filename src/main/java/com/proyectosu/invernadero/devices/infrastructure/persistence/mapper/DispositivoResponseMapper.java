package com.proyectosu.invernadero.devices.infrastructure.persistence.mapper;

import com.proyectosu.invernadero.devices.domain.model.Dispositivo;
import com.proyectosu.invernadero.devices.dto.response.DispositivoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DispositivoResponseMapper {

    @Mapping(target = "device_id", source = "deviceId")
    @Mapping(target = "created_at", source = "createdAt")
    DispositivoResponse toResponse(Dispositivo domain);
}