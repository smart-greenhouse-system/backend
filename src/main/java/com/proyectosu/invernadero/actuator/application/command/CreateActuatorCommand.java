package com.proyectosu.invernadero.actuator.application.command;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateActuatorCommand {

    private final String actuatorId;
    private final String deviceId;
    private final String actuador;
    private final String nombre;
    private final String estado;
    private final Boolean enabled;
}
