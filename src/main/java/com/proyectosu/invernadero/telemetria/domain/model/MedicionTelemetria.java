package com.proyectosu.invernadero.telemetria.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@AllArgsConstructor
public class MedicionTelemetria {

    private String id;
    private String deviceId;
    private Instant timestamp;
    private BigDecimal temperatura;
    private BigDecimal humedadRelativa;
    private BigDecimal humedadSuelo;
    private BigDecimal iluminacion;
    private BigDecimal co2;
}