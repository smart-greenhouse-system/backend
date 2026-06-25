package com.proyectosu.invernadero.actuator.application.usecases;

import com.proyectosu.invernadero.actuator.application.command.ExecuteActuatorCommand;
import com.proyectosu.invernadero.actuator.domain.model.Actuator;
import com.proyectosu.invernadero.actuator.domain.model.ActuatorEvent;
import com.proyectosu.invernadero.actuator.domain.port.ActuatorEventRepositoryPort;
import com.proyectosu.invernadero.actuator.domain.port.ActuatorRepositoryPort;
import com.proyectosu.invernadero.device.domain.port.DeviceRepositoryPort;
import com.proyectosu.invernadero.shared.domain.ports.MqttPublisherPort;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RequiredArgsConstructor
public class ExecuteActuatorUseCase {

    private static final Set<String> VALID_ACTIONS = Set.of("ON", "OFF");

    private final ActuatorEventRepositoryPort actuatorEventRepositoryPort;
    private final MqttPublisherPort mqttPublisherPort;
    private final ActuatorRepositoryPort actuatorRepositoryPort;
    private final DeviceRepositoryPort deviceRepositoryPort;

    public void execute(ExecuteActuatorCommand command) {
        validateCommand(command);

        String normalizedAction = command.getAction().trim().toUpperCase();
        Actuator actuator = validateDeviceAndActuator(command);

        publishActuatorCommand(actuator.getDeviceId(), actuator.getActuador(), normalizedAction);

        actuatorEventRepositoryPort.save(
                ActuatorEvent.fromManual(
                        actuator.getDeviceId(),
                        actuator.getActuador(),
                        normalizedAction
                )
        );
    }

    private Actuator validateDeviceAndActuator(ExecuteActuatorCommand command) {
        deviceRepositoryPort.findByDeviceId(command.getDeviceId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Dispositivo no encontrado: " + command.getDeviceId()
                ));

        Actuator actuator = actuatorRepositoryPort.findByDeviceIdAndActuador(
                        command.getDeviceId(),
                        command.getActuator()
                )
                .orElseThrow(() -> new IllegalArgumentException(
                        "Actuador no encontrado para el dispositivo: " + command.getDeviceId()
                ));

        if (!actuator.isEnabled()) {
            throw new IllegalArgumentException("Actuador deshabilitado: " + actuator.getActuatorId());
        }

        return actuator;
    }

    private void publishActuatorCommand(String deviceId, String actuator, String action) {
        String topic = buildTopic(deviceId, actuator);

        Map<String, Object> payload = new HashMap<>();
        payload.put("device_id", deviceId);
        payload.put("actuador", actuator);
        payload.put("accion", action);

        mqttPublisherPort.publicar(topic, payload);
    }

    private String buildTopic(String deviceId, String actuator) {
        return "invernadero/" + deviceId + "/actuadores/" + actuator + "/cmd";
    }

    private void validateCommand(ExecuteActuatorCommand command) {
        if (command == null) {
            throw new IllegalArgumentException("El comando del actuador no puede ser nulo");
        }

        if (isBlank(command.getDeviceId())) {
            throw new IllegalArgumentException("El deviceId es obligatorio");
        }

        if (isBlank(command.getActuator())) {
            throw new IllegalArgumentException("El actuador es obligatorio");
        }

        if (isBlank(command.getAction())) {
            throw new IllegalArgumentException("La acción es obligatoria");
        }

        String normalizedAction = command.getAction().trim().toUpperCase();
        if (!VALID_ACTIONS.contains(normalizedAction)) {
            throw new IllegalArgumentException("La acción debe ser ON u OFF");
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
