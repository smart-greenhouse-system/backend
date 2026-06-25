package com.proyectosu.invernadero.greenhouse.application.command;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateGreenhouseConfigCommand {

    private final String greenhouseName;
    private final Boolean automaticMode;
    private final Integer aiAnalysisFrequencyMinutes;
}
