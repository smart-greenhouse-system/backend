package com.proyectosu.invernadero.prediction.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreatePredictionResponse {
    private String message;

    private Boolean processed;

    @JsonProperty("automatic_mode")
    private Boolean automaticMode;

    @JsonProperty("actuator_executed")
    private Boolean actuatorExecuted;

    private Integer timeAction;
}
