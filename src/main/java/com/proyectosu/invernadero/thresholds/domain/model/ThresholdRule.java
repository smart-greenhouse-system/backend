package com.proyectosu.invernadero.thresholds.domain.model;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
public class ThresholdRule {

    private final String id;
    private final String greenhouseId;
    private final String variable;
    private final BigDecimal minValue;
    private final BigDecimal maxValue;
    private final String unit;
    private final String severity;
    private final boolean enabled;
    private final Instant createdAt;
    private final Instant updatedAt;

    public ThresholdRule(
            String id,
            String greenhouseId,
            String variable,
            BigDecimal minValue,
            BigDecimal maxValue,
            String unit,
            String severity,
            boolean enabled,
            Instant createdAt,
            Instant updatedAt
    ) {
        this.id = id;
        this.greenhouseId = greenhouseId;
        this.variable = variable;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.unit = unit;
        this.severity = severity;
        this.enabled = enabled;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}