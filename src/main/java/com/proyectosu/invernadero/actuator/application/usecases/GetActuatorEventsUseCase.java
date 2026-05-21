package com.proyectosu.invernadero.actuator.application.usecases;

import com.proyectosu.invernadero.actuator.domain.model.ActuatorEvent;
import com.proyectosu.invernadero.actuator.domain.port.ActuatorEventRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
public class GetActuatorEventsUseCase {

    private final ActuatorEventRepositoryPort actuatorEventRepositoryPort;

    public List<ActuatorEvent> execute() {
        return actuatorEventRepositoryPort.findAll()
                .stream()
                .sorted(Comparator.comparing(
                        ActuatorEvent::getCreatedAt,
                        Comparator.nullsLast(Comparator.reverseOrder())
                ))
                .toList();
    }

    public List<ActuatorEvent> executeByDeviceId(String deviceId) {
        if (isBlank(deviceId)) {
            throw new IllegalArgumentException("El deviceId es obligatorio");
        }

        return actuatorEventRepositoryPort.findByDeviceId(deviceId)
                .stream()
                .sorted(Comparator.comparing(
                        ActuatorEvent::getCreatedAt,
                        Comparator.nullsLast(Comparator.reverseOrder())
                ))
                .toList();
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
