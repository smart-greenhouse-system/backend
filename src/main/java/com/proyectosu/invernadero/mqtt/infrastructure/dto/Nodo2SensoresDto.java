package com.proyectosu.invernadero.mqtt.infrastructure.dto;

import java.time.Instant;

public record Nodo2SensoresDto(
        String deviceId,
        Double temperatura,
        Double iluminacion,
        Instant receivedAt
) {
}
