package com.proyectosu.invernadero.mqtt.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SensorNodo1 {
    private String deviceId;
    private Double humedadRelativa;
    private Double humedadSuelo;
    private String timestamp;
}
