package com.proyectosu.invernadero.greenhouse.application.usecases;

import com.proyectosu.invernadero.greenhouse.domain.model.GreenhouseConfig;
import com.proyectosu.invernadero.greenhouse.domain.port.GreenhouseConfigRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetGreenhouseConfigUseCase {

    private final GreenhouseConfigRepositoryPort greenhouseConfigRepositoryPort;

    public GreenhouseConfig execute() {
        return greenhouseConfigRepositoryPort.getCurrentOrCreateDefault();
    }
}
