package com.proyectosu.invernadero.mqtt.inbound.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SensorNodo2Request {

    @JsonProperty("device_id")
    private String deviceId;

    @JsonProperty("temperatura")
    private Double temperatura;

    @JsonProperty("iluminacion")
    private Integer iluminacion;

    @JsonProperty("timestamp")
    private String timestamp;
}
