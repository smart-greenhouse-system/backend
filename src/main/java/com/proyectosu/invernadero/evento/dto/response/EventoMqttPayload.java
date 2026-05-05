package com.proyectosu.invernadero.evento.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EventoMqttPayload {
    private String mensaje;
}
