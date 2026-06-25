package com.proyectosu.invernadero.actuator.application.command;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ExecuteTimedActuatorCommand {
    private final String actuatorId;
    private final Integer timeActionSeconds;
    private final String origin;
}
