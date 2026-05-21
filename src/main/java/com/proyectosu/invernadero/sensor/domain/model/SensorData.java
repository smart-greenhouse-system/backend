package com.proyectosu.invernadero.sensor.domain.model;

import lombok.Getter;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Getter
public class SensorData {

    private final String id;
    private final String deviceId;
    private final Map<String, Double> sensores;
    private final Instant createdAt;

    private SensorData(
            String id,
            String deviceId,
            Map<String, Double> sensores,
            Instant createdAt
    ) {
        this.id = id;
        this.deviceId = deviceId;
        this.sensores = sensores;
        this.createdAt = createdAt;
    }

    public static SensorData createFromMqtt(String deviceId, Map<String, Double> sensores) {
        return new SensorData(
                UUID.randomUUID().toString(),
                deviceId,
                sensores,
                Instant.now()
        );
    }

    public static SensorData restore(
            String id,
            String deviceId,
            Map<String, Double> sensores,
            Instant createdAt
    ) {
        return new SensorData(id, deviceId, sensores, createdAt);
    }
}
