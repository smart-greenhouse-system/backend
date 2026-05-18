package com.proyectosu.invernadero.mqtt.infrastructure.dto;

import java.time.Instant;

public record ActuatorStateMessageDto(
        String deviceId,
        String actuator,
        String estado,
        Instant receivedAt
) {
}
