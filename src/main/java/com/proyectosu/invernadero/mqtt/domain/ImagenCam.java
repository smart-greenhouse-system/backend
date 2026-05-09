package com.proyectosu.invernadero.mqtt.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImagenCam {
    private String deviceId;
    private String imagenBase64;
    private String timestamp;
}
