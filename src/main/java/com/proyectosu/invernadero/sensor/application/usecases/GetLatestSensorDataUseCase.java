package com.proyectosu.invernadero.sensor.application.usecases;

import com.proyectosu.invernadero.sensor.domain.model.SensorData;
import com.proyectosu.invernadero.sensor.domain.port.SensorDataRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetLatestSensorDataUseCase {

    private final SensorDataRepositoryPort sensorDataRepositoryPort;

    public SensorData execute() {
        return sensorDataRepositoryPort.findLatest()
                .orElseThrow(() -> new IllegalArgumentException("No hay lecturas de sensores registradas"));
    }
}
