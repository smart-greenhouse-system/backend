package com.proyectosu.invernadero.mqtt.infrastructure.mapper;

import com.proyectosu.invernadero.mqtt.infrastructure.dto.ActuatorStateMessageDto;
import org.springframework.stereotype.Component;
import tools.jackson.databind.JsonNode;

import java.time.Instant;

@Component
public class ActuatorStateMapper {

    public ActuatorStateMessageDto toDto(String topic, JsonNode payload) {
        String[] parts = topic.split("/");
        if (parts.length != 5) {
            throw new IllegalArgumentException("Topico de estado de actuador invalido: " + topic);
        }

        String estado = payload.hasNonNull("estado") ? payload.get("estado").asText() : null;
        if (estado == null || estado.isBlank()) {
            throw new IllegalArgumentException("Payload de estado de actuador invalido: estado vacio");
        }

        return new ActuatorStateMessageDto(parts[1], parts[3], estado, Instant.now());
    }
}
