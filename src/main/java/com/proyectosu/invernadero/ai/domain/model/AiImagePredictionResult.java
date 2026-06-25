package com.proyectosu.invernadero.ai.domain.model;

import lombok.Getter;

@Getter
public class AiImagePredictionResult {

    private final String cultivo;
    private final boolean success;
    private final String estadoPlanta;
    private final Double confianza;
    private final Integer tiempoCosechaDias;

    private AiImagePredictionResult(
            String cultivo,
            boolean success,
            String estadoPlanta,
            Double confianza,
            Integer tiempoCosechaDias
    ) {
        this.cultivo = cultivo;
        this.success = success;
        this.estadoPlanta = estadoPlanta;
        this.confianza = confianza;
        this.tiempoCosechaDias = tiempoCosechaDias;
    }

    public static AiImagePredictionResult restore(
            String cultivo,
            boolean success,
            String estadoPlanta,
            Double confianza,
            Integer tiempoCosechaDias
    ) {
        return new AiImagePredictionResult(
                cultivo,
                success,
                estadoPlanta,
                confianza,
                tiempoCosechaDias
        );
    }
}
