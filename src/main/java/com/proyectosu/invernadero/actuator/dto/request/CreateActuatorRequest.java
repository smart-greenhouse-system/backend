package com.proyectosu.invernadero.actuator.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateActuatorRequest {

    @JsonProperty("actuator_id")
    private String actuatorId;

    @JsonProperty("device_id")
    private String deviceId;

    private String actuador;

    private String nombre;

    private String estado;

    private Boolean enabled;
}
