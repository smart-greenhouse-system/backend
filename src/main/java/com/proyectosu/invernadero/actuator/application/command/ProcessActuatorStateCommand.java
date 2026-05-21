package com.proyectosu.invernadero.actuator.application.command;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProcessActuatorStateCommand {

    private final String deviceId;
    private final String actuador;
    private final String estado;
    private final Boolean executed;
    private final String topic;
}
