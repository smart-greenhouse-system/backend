package com.proyectosu.invernadero.image.application.command;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StoreImageCommand {

    private final String deviceId;
    private final String format;
    private final String resolution;
    private final String imageBase64;
}
