package com.proyectosu.invernadero.mqtt.inbound.mapper;

import com.proyectosu.invernadero.mqtt.domain.SensorNodo2;
import com.proyectosu.invernadero.mqtt.inbound.dto.request.SensorNodo2Request;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SensorNodo2InboundMapper {
    SensorNodo2 toDomain(SensorNodo2Request request);
}
