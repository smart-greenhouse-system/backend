package com.proyectosu.invernadero.actuator.application.command;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ExecuteActuatorCommand {
    private final String deviceId;
    private final String actuator;
    private final String action;
    private final String origin;
}
