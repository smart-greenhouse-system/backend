package com.proyectosu.invernadero.mqtt.infrastructure.dto;

import java.time.Instant;
import java.util.Map;

public record SensorReadingDto(
        String deviceId,
        Map<String, Object> sensores,
        Instant receivedAt
) {
}
