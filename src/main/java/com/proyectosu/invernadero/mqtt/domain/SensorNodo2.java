package com.proyectosu.invernadero.mqtt.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SensorNodo2 {
    private String deviceId;
    private Double temperatura;
    private Double iluminacion;
    private String timestamp;
}
