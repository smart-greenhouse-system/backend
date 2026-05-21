package com.proyectosu.invernadero.prediction.domain.model;

import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
public class Prediction {

    private final String id;
    private final String deviceId;
    private final String actuatorId;
    private final Integer timeAction;
    private boolean processed;
    private boolean automaticMode;
    private boolean executed;
    private final Instant createdAt;

    private Prediction(
            String id,
            String deviceId,
            String actuatorId,
            Integer timeAction,
            boolean processed,
            boolean automaticMode,
            boolean executed,
            Instant createdAt
    ) {
        this.id = id;
        this.deviceId = deviceId;
        this.actuatorId = actuatorId;
        this.timeAction = timeAction;
        this.processed = processed;
        this.automaticMode = automaticMode;
        this.executed = executed;
        this.createdAt = createdAt;
    }

    public static Prediction restore(
            String id,
            String deviceId,
            String actuatorId,
            Integer timeAction,
            boolean processed,
            boolean automaticMode,
            boolean executed,
            Instant createdAt
    ) {
        return new Prediction(
                id,
                deviceId,
                actuatorId,
                timeAction,
                processed,
                automaticMode,
                executed,
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
                deviceId,
                actuatorId,
                timeAction,
                false,
                false,
                false,
                Instant.now()
        );
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
