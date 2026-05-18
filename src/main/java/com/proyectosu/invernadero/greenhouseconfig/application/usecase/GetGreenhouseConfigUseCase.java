package com.proyectosu.invernadero.greenhouseconfig.application.usecase;

import com.proyectosu.invernadero.greenhouseconfig.domain.model.GreenhouseConfig;
import com.proyectosu.invernadero.greenhouseconfig.domain.ports.GreenhouseConfigRepositoryPort;

public class GetGreenhouseConfigUseCase {

    private final GreenhouseConfigRepositoryPort repositoryPort;

    public GetGreenhouseConfigUseCase(GreenhouseConfigRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    public GreenhouseConfig ejecutar() {
        return repositoryPort.read();
    }
}