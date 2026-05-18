package com.proyectosu.invernadero.greenhouseconfig.domain.model;

import lombok.Getter;

import java.time.Instant;

@Getter
public class GreenhouseConfig {

    private final String configKey;
    private final Boolean automaticMode;
    private final Integer inactivityThresholdMinutes;
    private final Integer manualOverrideDurationMinutes;
    private final String reportTimezone;
    private final Instant updatedAt;

    public GreenhouseConfig(
            String configKey,
            Boolean automaticMode,
            Integer inactivityThresholdMinutes,
            Integer manualOverrideDurationMinutes,
            String reportTimezone,
            Instant updatedAt
    ) {
        this.configKey = configKey;
        this.automaticMode = automaticMode;
        this.inactivityThresholdMinutes = inactivityThresholdMinutes;
        this.manualOverrideDurationMinutes = manualOverrideDurationMinutes;
        this.reportTimezone = reportTimezone;
        this.updatedAt = updatedAt;
    }

    public GreenhouseConfig with(
            Boolean automaticMode,
            Integer inactivityThresholdMinutes,
            Integer manualOverrideDurationMinutes,
            String reportTimezone,
            Instant updatedAt
    ) {
        return new GreenhouseConfig(
                configKey,
                automaticMode,
                inactivityThresholdMinutes,
                manualOverrideDurationMinutes,
                reportTimezone,
                updatedAt
        );
    }
}