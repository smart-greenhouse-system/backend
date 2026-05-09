package com.proyectosu.invernadero.mqtt.inbound.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstadoActuadorRequest {

    @JsonProperty("device_id")
    private String deviceId;

    @JsonProperty("actuador")
    private String actuador;

    @JsonProperty("estado")
    private String estado;

    @JsonProperty("ejecutado")
    private Boolean ejecutado;

    @JsonProperty("timestamp")
    private String timestamp;
}
