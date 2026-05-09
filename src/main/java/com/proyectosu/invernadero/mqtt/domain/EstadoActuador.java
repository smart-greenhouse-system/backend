package com.proyectosu.invernadero.mqtt.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstadoActuador {
    private String deviceId;
    private String actuador;
    private String estado;
    private Boolean ejecutado;
    private String timestamp;
}
