package com.proyectosu.invernadero.actuator.application.usecases;

import com.proyectosu.invernadero.actuator.application.command.ProcessActuatorStateCommand;
import com.proyectosu.invernadero.actuator.domain.model.Actuator;
import com.proyectosu.invernadero.actuator.domain.model.ActuatorEvent;
import com.proyectosu.invernadero.actuator.domain.port.ActuatorEventRepositoryPort;
import com.proyectosu.invernadero.actuator.domain.port.ActuatorRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProcessActuatorStateUseCase {

    private final ActuatorRepositoryPort actuatorRepositoryPort;
    private final ActuatorEventRepositoryPort actuatorEventRepositoryPort;

    public ActuatorEvent execute(ProcessActuatorStateCommand command) {
        validateCommand(command);

        Actuator actuator = actuatorRepositoryPort.findByDeviceIdAndActuador(
                        command.getDeviceId(),
                        command.getActuador()
                )
                .orElseThrow(() -> new IllegalArgumentException(
                        "Actuador no encontrado para el dispositivo: " + command.getDeviceId()
                ));

        String normalizedEstado = normalizeEstado(command.getEstado());

        Actuator updatedActuator = actuator.applyPartialUpdate(
                null,
                null,
                null,
                normalizedEstado,
                null
        );

        actuatorRepositoryPort.save(updatedActuator);

        ActuatorEvent event = ActuatorEvent.fromIotConfirmation(
                command.getDeviceId(),
                command.getActuador(),
                normalizedEstado,
                Boolean.TRUE.equals(command.getExecuted()),
                command.getTopic()
        );

        return actuatorEventRepositoryPort.save(event);
    }

    private void validateCommand(ProcessActuatorStateCommand command) {
        if (command == null) {
            throw new IllegalArgumentException("El comando de estado del actuador no puede ser nulo");
        }

        if (isBlank(command.getDeviceId())) {
            throw new IllegalArgumentException("El deviceId es obligatorio");
        }

        if (isBlank(command.getActuador())) {
            throw new IllegalArgumentException("El actuador es obligatorio");
        }

        if (isBlank(command.getEstado())) {
            throw new IllegalArgumentException("El estado es obligatorio");
        }

        if (command.getExecuted() == null) {
            throw new IllegalArgumentException("El campo ejecutado es obligatorio");
        }

        normalizeEstado(command.getEstado());
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
