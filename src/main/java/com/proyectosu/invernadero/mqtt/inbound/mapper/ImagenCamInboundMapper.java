package com.proyectosu.invernadero.mqtt.inbound.mapper;

import com.proyectosu.invernadero.mqtt.domain.ImagenCam;
import com.proyectosu.invernadero.mqtt.inbound.dto.request.ImagenCamRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ImagenCamInboundMapper {
    ImagenCam toDomain(ImagenCamRequest request);
}
