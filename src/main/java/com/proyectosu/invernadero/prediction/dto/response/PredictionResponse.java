package com.proyectosu.invernadero.prediction.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
public class PredictionResponse {
    private String id;

    @JsonProperty("device_id")
    private String deviceId;

    @JsonProperty("actuador_id")
    private String actuatorId;

    private Integer timeAction;

    private Boolean processed;

    @JsonProperty("automatic_mode")
    private Boolean automaticMode;

    private Boolean executed;

    @JsonProperty("created_at")
    private Instant createdAt;
}
