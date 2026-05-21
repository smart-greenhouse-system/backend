package com.proyectosu.invernadero.device.application.command;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class UpsertDeviceStatusCommand {

    private final String deviceId;
    private final List<String> sensores;
}
