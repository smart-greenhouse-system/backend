package com.proyectosu.invernadero.evento.infrastructure.mqtt;

import com.proyectosu.invernadero.evento.dto.response.EventoMqttPayload;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EventoMqttMapper {
    EventoMqttPayload toPayload(String mensaje);
}
