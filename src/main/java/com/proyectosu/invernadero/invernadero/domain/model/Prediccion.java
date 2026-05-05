package com.proyectosu.invernadero.auth.domain.model;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class Prediccion {
    private UUID id;
    private Dispositivo dispositivo;
    private LocalDateTime fechaHora;

    private BigDecimal temperaturaEstimada;
    private BigDecimal humedadRelativaEstimada;

    private String alerta;

    public Prediccion(UUID id,
                      Dispositivo dispositivo,
                      LocalDateTime fechaHora,
                      BigDecimal temperaturaEstimada,
                      BigDecimal humedadRelativaEstimada,
                      String alerta) {
        this.id = id;
        this.dispositivo = dispositivo;
        this.fechaHora = fechaHora;
        this.temperaturaEstimada = temperaturaEstimada;
        this.humedadRelativaEstimada = humedadRelativaEstimada;
        this.alerta = alerta;
    }
}
