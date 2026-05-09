package com.proyectosu.invernadero.telemetria.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@AllArgsConstructor
public class MedicionTelemetriaResponse {

    private Instant timestamp;
    private BigDecimal temperatura;
    private BigDecimal humedad_relativa;
    private BigDecimal humedad_suelo;
    private BigDecimal iluminacion;
    private BigDecimal co2;
}