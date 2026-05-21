package com.proyectosu.invernadero.greenhouse.domain.model;

import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
public class GreenhouseConfig {
    private final String id;
    private final String greenhouseName;
    private final boolean automaticMode;
    private final Integer aiAnalysisFrequencyMinutes;
    private final Instant createdAt;

    private GreenhouseConfig(
            String id,
            String greenhouseName,
            boolean automaticMode,
            Integer aiAnalysisFrequencyMinutes,
            Instant createdAt
    ) {
        this.id = id;
        this.greenhouseName = greenhouseName;
        this.automaticMode = automaticMode;
        this.aiAnalysisFrequencyMinutes = aiAnalysisFrequencyMinutes;
        this.createdAt = createdAt;
    }

    public static GreenhouseConfig createDefault() {
        return new GreenhouseConfig(
                UUID.randomUUID().toString(),
                "Smart Greenhouse",
                true,
                3,
                Instant.now()
        );
    }

    public static GreenhouseConfig restore(
            String id,
            String greenhouseName,
            boolean automaticMode,
            Integer aiAnalysisFrequencyMinutes,
            Instant createdAt
    ) {
        return new GreenhouseConfig(
                id,
                greenhouseName,
                automaticMode,
                aiAnalysisFrequencyMinutes,
                createdAt
        );
    }
}
