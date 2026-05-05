package com.proyectosu.invernadero.invernadero.domain.model;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class EstadoPlanta {
    private UUID id;
    private Dispositivo dispositivo;
    private LocalDateTime fechaHora;
    private String estado;

    public EstadoPlanta(UUID id,
                        Dispositivo dispositivo,
                        LocalDateTime fechaHora,
                        String estado) {
        this.id = id;
        this.dispositivo = dispositivo;
        this.fechaHora = fechaHora;
        this.estado = estado;
    }
}
