package com.proyectosu.invernadero.prediction.application.command;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SaveImageAnalysisPredictionCommand {

    private final String deviceId;
    private final String cultivo;
    private final boolean success;
    private final String estadoPlanta;
    private final Double confianza;
    private final Integer tiempoCosechaDias;
}
