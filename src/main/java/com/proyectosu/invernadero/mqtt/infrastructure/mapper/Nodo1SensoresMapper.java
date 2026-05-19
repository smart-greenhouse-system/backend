package com.proyectosu.invernadero.mqtt.infrastructure.mapper;

import com.proyectosu.invernadero.mqtt.infrastructure.dto.Nodo1SensoresDto;
import org.springframework.stereotype.Component;
import tools.jackson.databind.JsonNode;

import java.time.Instant;

@Component
public class Nodo1SensoresMapper {

    public Nodo1SensoresDto toDto(String deviceId, JsonNode payload) {
        JsonNode sensoresNode = payload.has("sensores") ? payload.get("sensores") : payload;
        if (!sensoresNode.isObject()) {
            throw new IllegalArgumentException("Payload de nodo1 invalido: 'sensores' debe ser objeto");
        }
        Double humedadRelativa = toNullableDouble(sensoresNode.get("humedad_relativa"));
        Double humedadSuelo = toNullableDouble(sensoresNode.get("humedad_suelo"));

        return new Nodo1SensoresDto(deviceId, humedadRelativa, humedadSuelo, Instant.now());
    }

    private Double toNullableDouble(JsonNode value) {
        return value != null && value.isNumber() ? value.asDouble() : null;
    }
}
