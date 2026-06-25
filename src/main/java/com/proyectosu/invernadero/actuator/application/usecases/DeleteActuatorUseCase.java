package com.proyectosu.invernadero.actuator.application.usecases;

import com.proyectosu.invernadero.actuator.domain.port.ActuatorRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeleteActuatorUseCase {

    private final ActuatorRepositoryPort actuatorRepositoryPort;

    public void execute(String actuatorId) {
        if (isBlank(actuatorId)) {
            throw new IllegalArgumentException("El actuatorId es obligatorio");
        }

        if (actuatorRepositoryPort.findByActuatorId(actuatorId).isEmpty()) {
            throw new IllegalArgumentException("Actuador no encontrado: " + actuatorId);
        }

        actuatorRepositoryPort.deleteByActuatorId(actuatorId);
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
