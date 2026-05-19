package com.proyectosu.invernadero.mqtt.infrastructure.mapper;

import com.proyectosu.invernadero.mqtt.infrastructure.dto.Nodo2SensoresDto;
import org.springframework.stereotype.Component;
import tools.jackson.databind.JsonNode;

import java.time.Instant;

@Component
public class Nodo2SensoresMapper {

    public Nodo2SensoresDto toDto(String deviceId, JsonNode payload) {
        JsonNode sensoresNode = payload.has("sensores") ? payload.get("sensores") : payload;
        if (!sensoresNode.isObject()) {
            throw new IllegalArgumentException("Payload de nodo2 invalido: 'sensores' debe ser objeto");
        }
        Double temperatura = toNullableDouble(sensoresNode.get("temperatura"));
        Double iluminacion = toNullableDouble(sensoresNode.get("iluminacion"));

        return new Nodo2SensoresDto(deviceId, temperatura, iluminacion, Instant.now());
    }

    private Double toNullableDouble(JsonNode value) {
        return value != null && value.isNumber() ? value.asDouble() : null;
    }
}
