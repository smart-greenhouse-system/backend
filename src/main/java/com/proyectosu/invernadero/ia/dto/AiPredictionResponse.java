package com.proyectosu.invernadero.ia.dto;

import java.time.Instant;
import java.util.Map;

public record AiPredictionResponse(
        boolean success,
        String model,
        String deviceId,
        Object result,
        String message,
        Instant analyzedAt,
        Map<String, Object> rawResponse
) {
}
