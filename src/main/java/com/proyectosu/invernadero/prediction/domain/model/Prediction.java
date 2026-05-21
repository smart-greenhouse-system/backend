package com.proyectosu.invernadero.prediction.domain.model;

import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
public class Prediction {

    public static final String TYPE_IMAGE_ANALYSIS = "IMAGE_ANALYSIS";

    private final String id;
    private final String tipo;
    private final String deviceId;
    private final String actuatorId;
    private final Integer timeAction;
    private boolean processed;
    private boolean automaticMode;
    private boolean executed;
    private final String cultivo;
    private final Boolean success;
    private final String estadoPlanta;
    private final Double confianza;
    private final Integer tiempoCosechaDias;
    private final Instant createdAt;

    private Prediction(
            String id,
            String tipo,
            String deviceId,
            String actuatorId,
            Integer timeAction,
            boolean processed,
            boolean automaticMode,
            boolean executed,
            String cultivo,
            Boolean success,
            String estadoPlanta,
            Double confianza,
            Integer tiempoCosechaDias,
            Instant createdAt
    ) {
        this.id = id;
        this.tipo = tipo;
        this.deviceId = deviceId;
        this.actuatorId = actuatorId;
        this.timeAction = timeAction;
        this.processed = processed;
        this.automaticMode = automaticMode;
        this.executed = executed;
        this.cultivo = cultivo;
        this.success = success;
        this.estadoPlanta = estadoPlanta;
        this.confianza = confianza;
        this.tiempoCosechaDias = tiempoCosechaDias;
        this.createdAt = createdAt;
    }

    public static Prediction restore(
            String id,
            String tipo,
            String deviceId,
            String actuatorId,
            Integer timeAction,
            boolean processed,
            boolean automaticMode,
            boolean executed,
            String cultivo,
            Boolean success,
            String estadoPlanta,
            Double confianza,
            Integer tiempoCosechaDias,
            Instant createdAt
    ) {
        return new Prediction(
                id,
                tipo,
                deviceId,
                actuatorId,
                timeAction,
                processed,
                automaticMode,
                executed,
                cultivo,
                success,
                estadoPlanta,
                confianza,
                tiempoCosechaDias,
                createdAt
        );
    }

    public static Prediction createFromAiRequest(
            String deviceId,
            String actuatorId,
            Integer timeAction
    ) {
        return new Prediction(
                UUID.randomUUID().toString(),
                null,
                deviceId,
                actuatorId,
                timeAction,
                false,
                false,
                false,
                null,
                null,
                null,
                null,
                null,
                Instant.now()
        );
    }

    public static Prediction createFromImageAnalysis(
            String deviceId,
            String cultivo,
            boolean success,
            String estadoPlanta,
            Double confianza,
            Integer tiempoCosechaDias
    ) {
        return new Prediction(
                UUID.randomUUID().toString(),
                TYPE_IMAGE_ANALYSIS,
                deviceId,
                null,
                null,
                false,
                false,
                false,
                cultivo,
                success,
                estadoPlanta,
                confianza,
                tiempoCosechaDias,
                Instant.now()
        );
    }

    public boolean isImageAnalysis() {
        return TYPE_IMAGE_ANALYSIS.equals(tipo);
    }

    public void markAsProcessed(boolean automaticMode, boolean executed) {
        this.processed = true;
        this.automaticMode = automaticMode;
        this.executed = executed;
    }

    public void markAsStoredWithoutExecution(boolean automaticMode) {
        this.processed = false;
        this.automaticMode = automaticMode;
        this.executed = false;
    }
}
