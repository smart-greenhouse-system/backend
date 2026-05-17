package com.proyectosu.invernadero.telemetria.domain.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MedicionTelemetria {

    private String id;
    private String deviceId;
    private Instant timestamp;
    private Map<String, BigDecimal> sensores;
}