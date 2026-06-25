package com.proyectosu.invernadero.prediction.application.command;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProcessAiPredictionCommand {
    private final String deviceId;
    private final Boolean processed;
    private final String actuatorId;
    private final Integer timeActionSeconds;
}
