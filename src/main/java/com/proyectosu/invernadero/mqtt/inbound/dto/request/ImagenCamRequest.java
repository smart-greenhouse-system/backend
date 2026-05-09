package com.proyectosu.invernadero.mqtt.inbound.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImagenCamRequest {

    @JsonProperty("device_id")
    private String deviceId;

    @JsonProperty("imagen_base64")
    private String imagenBase64;

    @JsonProperty("timestamp")
    private String timestamp;
}
