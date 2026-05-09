package com.proyectosu.invernadero.mqtt.inbound.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MqttRequest {

    @NotBlank(message = "El topico es obligatorio")
    private String topico;

    @NotBlank(message = "El mensaje es obligatorio")
    private String mensaje;
}
