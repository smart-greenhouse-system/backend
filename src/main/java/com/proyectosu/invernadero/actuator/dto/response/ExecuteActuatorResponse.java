package com.proyectosu.invernadero.actuator.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ExecuteActuatorResponse {
    private String message;
    private String deviceId;
    private String actuator;
    private String action;
    private Boolean executed;
}
