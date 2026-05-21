package com.proyectosu.invernadero.prediction.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreatePredictionRequest {
    @JsonProperty("device_id")
    private String deviceId;

    @JsonProperty("procesado")
    private Boolean processed;

    @JsonProperty("actuador_id")
    private String actuatorId;

    @JsonProperty("timeAction")
    private String timeAction;
}
