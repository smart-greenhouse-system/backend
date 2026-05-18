package com.proyectosu.invernadero.mqtt.infrastructure.dto;

import java.time.Instant;

public record CameraImageMessageDto(
        String deviceId,
        String imageBase64,
        Instant receivedAt
) {
}
