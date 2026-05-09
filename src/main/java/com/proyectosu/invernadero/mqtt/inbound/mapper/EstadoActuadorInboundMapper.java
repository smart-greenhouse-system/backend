package com.proyectosu.invernadero.mqtt.inbound.mapper;

import com.proyectosu.invernadero.mqtt.domain.EstadoActuador;
import com.proyectosu.invernadero.mqtt.inbound.dto.request.EstadoActuadorRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EstadoActuadorInboundMapper {
    EstadoActuador toDomain(EstadoActuadorRequest request);
}
