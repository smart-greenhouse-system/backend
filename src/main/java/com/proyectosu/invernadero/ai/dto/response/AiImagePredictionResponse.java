package com.proyectosu.invernadero.ai.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AiImagePredictionResponse {

    private String cultivo;

    private Boolean success;

    @JsonProperty("estado_planta")
    private String estadoPlanta;

    private Double confianza;

    @JsonProperty("tiempo_cosecha_dias")
    private Integer tiempoCosechaDias;
}
