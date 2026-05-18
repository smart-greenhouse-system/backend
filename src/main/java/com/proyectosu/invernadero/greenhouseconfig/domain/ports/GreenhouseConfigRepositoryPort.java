package com.proyectosu.invernadero.greenhouseconfig.domain.ports;

import com.proyectosu.invernadero.greenhouseconfig.domain.model.GreenhouseConfig;

public interface GreenhouseConfigRepositoryPort {

    GreenhouseConfig read();

    GreenhouseConfig save(GreenhouseConfig config);
}