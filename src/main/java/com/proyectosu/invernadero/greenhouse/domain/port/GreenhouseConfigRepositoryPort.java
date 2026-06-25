package com.proyectosu.invernadero.greenhouse.domain.port;

import com.proyectosu.invernadero.greenhouse.domain.model.GreenhouseConfig;

import java.util.Optional;

public interface GreenhouseConfigRepositoryPort {
    GreenhouseConfig save(GreenhouseConfig greenhouseConfig);

    Optional<GreenhouseConfig> findCurrent();

    GreenhouseConfig getCurrentOrCreateDefault();
}
