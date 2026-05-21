package com.proyectosu.invernadero.ai.application.command;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AnalyzeImageCommand {

    private final String deviceId;
    private final String imageBase64;
}
