package com.proyectosu.invernadero.shared.infrastructure.mqtt.handler;

import com.proyectosu.invernadero.telemetria.application.usecase.RegistrarMedicionUseCase;
import com.proyectosu.invernadero.telemetria.domain.model.MedicionTelemetria;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class SensorNodo1MessageHandler {

    private final ObjectMapper objectMapper;
    private final RegistrarMedicionUseCase registrarMedicionUseCase;

    public void handle(String topic, JsonNode payload){
        registrarMedicionUseCase.ejecutar(parsearMedicion(payload));
    }

    private MedicionTelemetria parsearMedicion(JsonNode payload) {
        String deviceId = payload.path("device_id").asText("").trim();

        if (deviceId.isBlank()) {
            throw new IllegalArgumentException("El device_id es obligatorio");
        }

        String timestampValue = payload.path("timestamp").asText("").trim();

        if (timestampValue.isBlank()) {
            throw new IllegalArgumentException("El timestamp es obligatorio");
        }

        Map<String, BigDecimal> sensores = extraerSensores(payload);

        if (sensores.isEmpty()) {
            throw new IllegalArgumentException("La medicion debe incluir sensores");
        }

        return new MedicionTelemetria(
                null,
                deviceId,
                Instant.parse(timestampValue),
                sensores
        );
    }

    private Map<String, BigDecimal> extraerSensores(JsonNode payload) {
        Map<String, Object> rawPayload = objectMapper.convertValue(payload, Map.class);
        Map<String, BigDecimal> sensores = new LinkedHashMap<>();
        Object nestedSensors = rawPayload.get("sensores");

        if (nestedSensors instanceof Map<?, ?> nestedSensorsMap) {
            nestedSensorsMap.forEach((key, value) -> {
                if (key != null && value instanceof Number number) {
                    sensores.put(String.valueOf(key), BigDecimal.valueOf(number.doubleValue()));
                }
            });
            return sensores;
        }

        rawPayload.forEach((fieldName, value) -> {
            if ("device_id".equals(fieldName) || "timestamp".equals(fieldName) || "sensores".equals(fieldName)) {
                return;
            }

            if (value instanceof Number number) {
                sensores.put(fieldName, BigDecimal.valueOf(number.doubleValue()));
            }
        });

        return sensores;
    }
}
