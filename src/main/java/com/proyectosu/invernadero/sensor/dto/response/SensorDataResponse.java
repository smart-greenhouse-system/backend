package com.proyectosu.invernadero.sensor.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.Map;

@Getter
@Builder
public class SensorDataResponse {

    private String id;

    @JsonProperty("device_id")
    private String deviceId;

    private Map<String, Double> sensores;

    @JsonProperty("created_at")
    private Instant createdAt;
}
