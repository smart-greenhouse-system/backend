package com.proyectosu.invernadero.mqtt.inbound.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SensorNodo1Request {

    @JsonProperty("device_id")
    private String deviceId;

    @JsonProperty("humedad_relativa")
    private Double humedadRelativa;

    @JsonProperty("humedad_suelo")
    private Double humedadSuelo;

    @JsonProperty("timestamp")
    private String timestamp;
}
