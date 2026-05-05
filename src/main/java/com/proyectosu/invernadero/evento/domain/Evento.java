package com.proyectosu.invernadero.auth.domain.model.prueba;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor

public class Evento {
    private String origen;
    private String tipo;
    private String mensaje;
}
