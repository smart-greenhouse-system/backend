package com.proyectosu.invernadero.telemetria.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;

@Getter
@AllArgsConstructor
public class MedicionTelemetriaResponse {

    private Instant timestamp;
    private Map<String, BigDecimal> sensores;
}