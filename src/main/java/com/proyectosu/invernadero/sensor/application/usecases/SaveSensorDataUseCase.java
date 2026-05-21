package com.proyectosu.invernadero.sensor.application.usecases;

import com.proyectosu.invernadero.sensor.application.command.SaveSensorDataCommand;
import com.proyectosu.invernadero.sensor.domain.model.SensorData;
import com.proyectosu.invernadero.sensor.domain.port.SensorDataRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class SaveSensorDataUseCase {

    private final SensorDataRepositoryPort sensorDataRepositoryPort;

    public SensorData execute(SaveSensorDataCommand command) {
        validateCommand(command);

        SensorData sensorData = SensorData.createFromMqtt(
                command.getDeviceId(),
                command.getSensores()
        );

        return sensorDataRepositoryPort.save(sensorData);
    }

    private void validateCommand(SaveSensorDataCommand command) {
        if (command == null) {
            throw new IllegalArgumentException("El comando de sensores no puede ser nulo");
        }

        if (isBlank(command.getDeviceId())) {
            throw new IllegalArgumentException("El deviceId es obligatorio");
        }

        if (command.getSensores() == null || command.getSensores().isEmpty()) {
            throw new IllegalArgumentException("Los datos de sensores son obligatorios");
        }

        for (Map.Entry<String, Double> entry : command.getSensores().entrySet()) {
            if (isBlank(entry.getKey())) {
                throw new IllegalArgumentException("El nombre del sensor no puede estar vacío");
            }

            if (entry.getValue() == null) {
                throw new IllegalArgumentException("El valor del sensor " + entry.getKey() + " es obligatorio");
            }
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
