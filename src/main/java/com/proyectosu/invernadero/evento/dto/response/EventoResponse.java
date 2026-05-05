package com.proyectosu.invernadero.evento.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EventoResponse {
    private String origen;
    private String tipo;
    private String mensaje;;
}
