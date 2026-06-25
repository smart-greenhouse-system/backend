package com.proyectosu.invernadero.greenhouse.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GreenhouseConfigResponse {

    @JsonProperty("nombre_invernadero")
    private String nombreInvernadero;

    @JsonProperty("modo_automatico")
    private Boolean modoAutomatico;

    @JsonProperty("frecuencia_analisis_ia_min")
    private Integer frecuenciaAnalisisIaMin;
}
