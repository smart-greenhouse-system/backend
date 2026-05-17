package com.proyectosu.invernadero.actuators.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@Getter
@AllArgsConstructor
public class ActuatorCommandResponse {

    private String device_id;
    private String actuador;
    private String accion;
    private String topic;
    private String estado;
    private Instant timestamp;
}