package com.proyectosu.invernadero.greenhouse.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateGreenhouseConfigRequest {

    @JsonProperty("nombre_invernadero")
    private String nombreInvernadero;

    @JsonProperty("modo_automatico")
    private Boolean modoAutomatico;

    @JsonProperty("frecuencia_analisis_ia_min")
    private Integer frecuenciaAnalisisIaMin;
}
