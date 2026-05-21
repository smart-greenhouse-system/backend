package com.proyectosu.invernadero.actuator.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
public class ActuatorResponse {

    @JsonProperty("actuator_id")
    private String actuatorId;

    @JsonProperty("device_id")
    private String deviceId;

    private String actuador;

    private String nombre;

    private String estado;

    private Boolean enabled;

    @JsonProperty("created_at")
    private Instant createdAt;

    @JsonProperty("updated_at")
    private Instant updatedAt;
}
