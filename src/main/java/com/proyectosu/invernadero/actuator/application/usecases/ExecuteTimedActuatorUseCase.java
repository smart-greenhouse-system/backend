package com.proyectosu.invernadero.actuator.application.usecases;

import com.proyectosu.invernadero.actuator.application.command.ExecuteTimedActuatorCommand;
import com.proyectosu.invernadero.actuator.domain.model.ActuatorEvent;
import com.proyectosu.invernadero.actuator.domain.model.ActuatorTarget;
import com.proyectosu.invernadero.actuator.domain.port.ActuatorEventRepositoryPort;
import com.proyectosu.invernadero.actuator.domain.port.ActuatorResolverPort;
import com.proyectosu.invernadero.prediction.domain.port.TimedActuatorExecutorPort;
import com.proyectosu.invernadero.shared.domain.ports.MqttPublisherPort;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.TaskScheduler;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class ExecuteTimedActuatorUseCase implements TimedActuatorExecutorPort {
    private static final String ACTION_ON = "ON";
    private static final String ACTION_OFF = "OFF";
    private static final String ORIGIN_IA = "IA";

    private final ActuatorResolverPort actuatorResolverPort;
    private final ActuatorEventRepositoryPort actuatorEventRepositoryPort;
    private final MqttPublisherPort mqttPublisherPort;
    private final TaskScheduler taskScheduler;

    @Override
    public void execute(String actuatorId, Integer timeActionSeconds) {
        ExecuteTimedActuatorCommand command = ExecuteTimedActuatorCommand.builder()
                .actuatorId(actuatorId)
                .timeActionSeconds(timeActionSeconds)
                .origin(ORIGIN_IA)
                .build();

        execute(command);
    }

    public void execute(ExecuteTimedActuatorCommand command) {
        validateCommand(command);

        ActuatorTarget target = actuatorResolverPort.resolve(command.getActuatorId());

        publishActuatorCommand(target, ACTION_ON);

        actuatorEventRepositoryPort.save(
                ActuatorEvent.fromIa(
                        target.getDeviceId(),
                        target.getActuator(),
                        ACTION_ON,
                        command.getTimeActionSeconds()
                )
        );

        taskScheduler.schedule(
                () -> turnOffActuator(target),
                Instant.now().plusSeconds(command.getTimeActionSeconds())
        );
    }

    private void turnOffActuator(ActuatorTarget target) {
        publishActuatorCommand(target, ACTION_OFF);

        actuatorEventRepositoryPort.save(
                ActuatorEvent.fromIa(
                        target.getDeviceId(),
                        target.getActuator(),
                        ACTION_OFF,
                        null
                )
        );
    }

    private void publishActuatorCommand(ActuatorTarget target, String action) {
        String topic = buildTopic(target.getDeviceId(), target.getActuator());

        Map<String, Object> payload = new HashMap<>();
        payload.put("device_id", target.getDeviceId());
        payload.put("actuador", target.getActuator());
        payload.put("accion", action);

        mqttPublisherPort.publicar(topic, payload);
    }

    private String buildTopic(String deviceId, String actuator) {
        return "invernadero/" + deviceId + "/actuadores/" + actuator + "/cmd";
    }

    private void validateCommand(ExecuteTimedActuatorCommand command) {
        if (command == null) {
            throw new IllegalArgumentException("El comando del actuador no puede ser nulo");
        }

        if (isBlank(command.getActuatorId())) {
            throw new IllegalArgumentException("El actuatorId es obligatorio");
        }

        if (command.getTimeActionSeconds() == null) {
            throw new IllegalArgumentException("El timeAction es obligatorio");
        }

        if (command.getTimeActionSeconds() <= 0) {
            throw new IllegalArgumentException("El timeAction debe ser mayor a 0 segundos");
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
