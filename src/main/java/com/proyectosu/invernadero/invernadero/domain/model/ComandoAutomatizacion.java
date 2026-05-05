package com.proyectosu.invernadero.auth.domain.model;

import com.proyectosu.invernadero.auth.domain.model.enums.EstadoComando;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class ComandoAutomatizacion {
    private UUID id;
    private Dispositivo dispositivo;
    private LocalDateTime fechaHora;

    private String accion;
    private Integer valor;
    private EstadoComando estado;

    public ComandoAutomatizacion(UUID id,
                                 Dispositivo dispositivo,
                                 LocalDateTime fechaHora,
                                 String accion,
                                 Integer valor,
                                 EstadoComando estado) {
        this.id = id;
        this.dispositivo = dispositivo;
        this.fechaHora = fechaHora;
        this.accion = accion;
        this.valor = valor;
        this.estado = estado;
    }
}
