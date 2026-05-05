package com.proyectosu.invernadero.rest.mapper;

import com.proyectosu.invernadero.evento.dto.response.EventoMqttPayload;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EventoMqttMapper {
    EventoMqttPayload toPayload(String mensaje);
}
