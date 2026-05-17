package com.proyectosu.invernadero.actuators.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@Getter
@AllArgsConstructor
public class ActuatorEvent {

    private String deviceId;
    private String actuador;
    private String accion;
    private String topic;
    private String estado;
    private Instant timestamp;
}