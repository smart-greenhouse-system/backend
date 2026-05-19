package com.proyectosu.invernadero.predictions.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@Getter
@AllArgsConstructor
public class Prediccion {

    private final String id;
    private final String tipo;
    private final String mensaje;
    private final String accionRecomendada;
    private final String deviceId;
    private final Boolean procesado;
    private final Instant createdAt;
}