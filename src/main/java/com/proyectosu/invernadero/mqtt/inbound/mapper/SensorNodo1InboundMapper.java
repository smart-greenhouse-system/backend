package com.proyectosu.invernadero.mqtt.inbound.mapper;

import com.proyectosu.invernadero.mqtt.domain.SensorNodo1;
import com.proyectosu.invernadero.mqtt.inbound.dto.request.SensorNodo1Request;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SensorNodo1InboundMapper {
    SensorNodo1 toDomain(SensorNodo1Request request);
}
