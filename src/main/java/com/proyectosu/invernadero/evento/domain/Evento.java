package com.proyectosu.invernadero.evento.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor

public class Evento {
    private String origen;
    private String tipo;
    private String mensaje;
}
