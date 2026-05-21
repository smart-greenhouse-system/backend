package com.proyectosu.invernadero.prediction.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
public class ImageAnalysisPredictionResponse {

    private String id;

    private String tipo;

    @JsonProperty("device_id")
    private String deviceId;

    private String cultivo;

    private Boolean success;

    @JsonProperty("estado_planta")
    private String estadoPlanta;

    private Double confianza;

    @JsonProperty("tiempo_cosecha_dias")
    private Integer tiempoCosechaDias;

    @JsonProperty("created_at")
    private Instant createdAt;
}
