package com.proyectosu.invernadero.sensor.application.usecases;

import com.proyectosu.invernadero.sensor.domain.model.SensorData;
import com.proyectosu.invernadero.sensor.domain.port.SensorDataRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GetSensorHistoryUseCase {

    private final SensorDataRepositoryPort sensorDataRepositoryPort;

    public List<SensorData> execute(String deviceId) {
        if (isBlank(deviceId)) {
            throw new IllegalArgumentException("El deviceId es obligatorio");
        }

        return sensorDataRepositoryPort.findHistoryByDeviceId(deviceId);
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
