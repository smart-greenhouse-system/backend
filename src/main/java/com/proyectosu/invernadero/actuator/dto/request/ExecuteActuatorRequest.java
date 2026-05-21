package com.proyectosu.invernadero.actuator.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ExecuteActuatorRequest {

    @JsonProperty("device_id")
    private String deviceId;

    @JsonProperty("actuador")
    private String actuator;

    @JsonProperty("accion")
    private String action;
}
