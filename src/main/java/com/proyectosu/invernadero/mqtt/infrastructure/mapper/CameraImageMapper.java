package com.proyectosu.invernadero.mqtt.infrastructure.mapper;

import com.proyectosu.invernadero.mqtt.infrastructure.dto.CameraImageMessageDto;
import org.springframework.stereotype.Component;
import tools.jackson.databind.JsonNode;

import java.time.Instant;

@Component
public class CameraImageMapper {

    public CameraImageMessageDto toDto(JsonNode payload) {
        String imageBase64 = payload.hasNonNull("imagen")
                ? payload.get("imagen").asText()
                : null;
        if (imageBase64 == null || imageBase64.isBlank()) {
            throw new IllegalArgumentException("Payload de camara invalido: imagen vacia");
        }
        return new CameraImageMessageDto("cam", imageBase64, Instant.now());
    }
}
