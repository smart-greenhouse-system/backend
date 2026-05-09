package com.proyectosu.invernadero.mqtt.inbound.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComandoActuadorResponse {

    @JsonProperty("device_id")
    private String deviceId;

    @JsonProperty("actuador")
    private String actuador;

    @JsonProperty("accion")
    private String accion;

    @JsonProperty("timestamp")
    private String timestamp;
}
