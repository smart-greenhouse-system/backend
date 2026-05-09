package com.proyectosu.invernadero.mqtt.infrastructure.persistence.mapper;

import com.proyectosu.invernadero.mqtt.domain.EstadoActuador;
import com.proyectosu.invernadero.mqtt.infrastructure.persistence.document.EstadoActuadorDocument;
import org.springframework.stereotype.Component;

@Component
public class EstadoActuadorMapper {

    public EstadoActuador toDomain(EstadoActuadorDocument document) {
        return new EstadoActuador(
                document.getDeviceId(),
                document.getActuador(),
                document.getEstado(),
                document.getEjecutado(),
                document.getTimestamp()
        );
    }

    public EstadoActuadorDocument toDocument(EstadoActuador domain) {
        return new EstadoActuadorDocument(
                null,
                domain.getDeviceId(),
                domain.getActuador(),
                domain.getEstado(),
                domain.getEjecutado(),
                domain.getTimestamp()
        );
    }
}
