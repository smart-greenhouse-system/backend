package com.proyectosu.invernadero.sensor.application.command;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class SaveSensorDataCommand {

    private final String deviceId;
    private final Map<String, Double> sensores;
}
