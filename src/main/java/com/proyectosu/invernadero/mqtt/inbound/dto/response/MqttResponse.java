package com.proyectosu.invernadero.mqtt.inbound.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MqttResponse {
    private String topico;
    private String mensaje;
}
