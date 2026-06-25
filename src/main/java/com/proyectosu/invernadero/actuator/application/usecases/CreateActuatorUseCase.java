package com.proyectosu.invernadero.actuator.application.usecases;

import com.proyectosu.invernadero.actuator.application.command.CreateActuatorCommand;
import com.proyectosu.invernadero.actuator.domain.model.Actuator;
import com.proyectosu.invernadero.actuator.domain.port.ActuatorRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateActuatorUseCase {

    private final ActuatorRepositoryPort actuatorRepositoryPort;

    public Actuator execute(CreateActuatorCommand command) {
        validateCommand(command);

        if (actuatorRepositoryPort.existsByActuatorId(command.getActuatorId())) {
            throw new IllegalArgumentException("Ya existe un actuador con id: " + command.getActuatorId());
        }

        Actuator actuator = Actuator.createNew(
                command.getActuatorId(),
                command.getDeviceId(),
                command.getActuador(),
                command.getNombre(),
                normalizeEstado(command.getEstado()),
                command.getEnabled() == null || command.getEnabled()
        );

        return actuatorRepositoryPort.save(actuator);
    }

    private void validateCommand(CreateActuatorCommand command) {
        if (command == null) {
            throw new IllegalArgumentException("El comando del actuador no puede ser nulo");
        }

        if (isBlank(command.getActuatorId())) {
            throw new IllegalArgumentException("El actuatorId es obligatorio");
        }

        if (isBlank(command.getDeviceId())) {
            throw new IllegalArgumentException("El deviceId es obligatorio");
        }

        if (isBlank(command.getActuador())) {
            throw new IllegalArgumentException("El actuador es obligatorio");
        }

        if (isBlank(command.getNombre())) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }

        validateEstado(command.getEstado());
    }

    private void validateEstado(String estado) {
        if (estado == null) {
            return;
        }

        normalizeEstado(estado);
    }

    private String normalizeEstado(String estado) {
        if (estado == null || estado.trim().isEmpty()) {
            return Actuator.STATUS_OFF;
        }

        String normalizedEstado = estado.trim().toUpperCase();
        if (!Actuator.STATUS_ON.equals(normalizedEstado) && !Actuator.STATUS_OFF.equals(normalizedEstado)) {
            throw new IllegalArgumentException("El estado debe ser ON u OFF");
        }

        return normalizedEstado;
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
