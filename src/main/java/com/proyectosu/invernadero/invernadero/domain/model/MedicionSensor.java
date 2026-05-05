package com.proyectosu.invernadero.invernadero.domain.model;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class MedicionSensor {

    private UUID id;
    private Dispositivo dispositivo;
    private LocalDateTime fechaHora;

    private BigDecimal temperatura;
    private BigDecimal humedadSuelo;
    private BigDecimal humedadRelativa;
    private BigDecimal luz;

    public MedicionSensor(UUID id,
                          Dispositivo dispositivo,
                          LocalDateTime fechaHora,
                          BigDecimal temperatura,
                          BigDecimal humedadSuelo,
                          BigDecimal humedadRelativa,
                          BigDecimal luz) {
        this.id = id;
        this.dispositivo = dispositivo;
        this.fechaHora = fechaHora;
        this.temperatura = temperatura;
        this.humedadSuelo = humedadSuelo;
        this.humedadRelativa = humedadRelativa;
        this.luz = luz;
    }
}
