package com.proyectosu.invernadero.prediction.domain.port;

import com.proyectosu.invernadero.greenhouse.domain.model.GreenhouseConfig;

public interface GreenhouseConfigReaderPort {
    GreenhouseConfig getCurrentConfig();
}
