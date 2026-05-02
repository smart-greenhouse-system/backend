package com.proyectosu.invernadero.domain.model.prueba;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor

public class RegistroEvento {
    private String origen;
    private String tipo;
    private String mensaje;
}
