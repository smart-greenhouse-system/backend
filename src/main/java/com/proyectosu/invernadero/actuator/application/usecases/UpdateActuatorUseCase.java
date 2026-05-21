package com.proyectosu.invernadero.actuator.application.usecases;

import com.proyectosu.invernadero.actuator.application.command.UpdateActuatorCommand;
import com.proyectosu.invernadero.actuator.domain.model.Actuator;
import com.proyectosu.invernadero.actuator.domain.port.ActuatorRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UpdateActuatorUseCase {

    private final ActuatorRepositoryPort actuatorRepositoryPort;

    public Actuator execute(UpdateActuatorCommand command) {
        validateCommand(command);

        Actuator currentActuator = actuatorRepositoryPort.findByActuatorId(command.getActuatorId())
                .orElseThrow(() -> new IllegalArgumentException("Actuador no encontrado: " + command.getActuatorId()));

        Actuator updatedActuator = currentActuator.applyPartialUpdate(
                command.getDeviceId(),
                command.getActuador(),
                command.getNombre(),
                command.getEstado() != null ? normalizeEstado(command.getEstado()) : null,
                command.getEnabled()
        );

        return actuatorRepositoryPort.save(updatedActuator);
    }

    private void validateCommand(UpdateActuatorCommand command) {
        if (command == null) {
            throw new IllegalArgumentException("El comando del actuador no puede ser nulo");
        }

        if (isBlank(command.getActuatorId())) {
            throw new IllegalArgumentException("El actuatorId es obligatorio");
        }

        if (command.getDeviceId() == null
                && command.getActuador() == null
                && command.getNombre() == null
                && command.getEstado() == null
                && command.getEnabled() == null) {
            throw new IllegalArgumentException("Debe enviar al menos un campo para actualizar");
        }

        if (command.getDeviceId() != null && isBlank(command.getDeviceId())) {
            throw new IllegalArgumentException("El deviceId no puede estar vacío");
        }

        if (command.getActuador() != null && isBlank(command.getActuador())) {
            throw new IllegalArgumentException("El actuador no puede estar vacío");
        }

        if (command.getNombre() != null && isBlank(command.getNombre())) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }

        if (command.getEstado() != null) {
            normalizeEstado(command.getEstado());
        }
    }

    private String normalizeEstado(String estado) {
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
