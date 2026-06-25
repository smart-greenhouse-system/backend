package com.proyectosu.invernadero.device.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.List;

@Getter
@Builder
public class DeviceResponse {

    @JsonProperty("device_id")
    private String deviceId;

    private String nombre;

    private String tipo;

    private String estado;

    private List<String> sensores;

    private List<String> actuadores;

    @JsonProperty("last_seen")
    private Instant lastSeen;

    @JsonProperty("created_at")
    private Instant createdAt;
}
