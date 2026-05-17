package com.proyectosu.invernadero.actuators.dto.request;

import lombok.Getter;

@Getter
public class ActuatorCommandRequest {

    private String device_id;
    private String actuador;
    private String accion;
}