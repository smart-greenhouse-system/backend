package com.proyectosu.invernadero.domain.model;

import com.proyectosu.invernadero.domain.model.enums.EstadoDispositivo;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class Dispositivo {
    private UUID id;
    private String codigo;
    private String nombre;
    private EstadoDispositivo estado;
    private LocalDateTime fechaCreacion;

    public Dispositivo(UUID id, String codigo, String nombre,
                       EstadoDispositivo estado,
                       LocalDateTime fechaCreacion) {
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
    }

}
