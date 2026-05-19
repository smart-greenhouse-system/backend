package com.proyectosu.invernadero.mqtt.infrastructure.dto;

import java.time.Instant;

public record Nodo1SensoresDto(
        String deviceId,
        Double humedadRelativa,
        Double humedadSuelo,
        Instant receivedAt
) {
}
