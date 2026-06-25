package com.proyectosu.invernadero.sensor.domain.port;

import com.proyectosu.invernadero.sensor.domain.model.SensorData;

import java.util.List;
import java.util.Optional;

public interface SensorDataRepositoryPort {

    SensorData save(SensorData sensorData);

    Optional<SensorData> findLatest();

    List<SensorData> findHistoryByDeviceId(String deviceId);
}
