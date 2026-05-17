package com.proyectosu.invernadero.actuators.application.usecase;

import com.proyectosu.invernadero.actuators.domain.model.ActuatorEvent;
import com.proyectosu.invernadero.actuators.domain.ports.ActuatorEventRepositoryPort;
import com.proyectosu.invernadero.devices.domain.model.Dispositivo;
import com.proyectosu.invernadero.devices.domain.ports.DispositivoRepositoryPort;
import com.proyectosu.invernadero.shared.domain.ports.MqttPublisherPort;
import org.springframework.beans.factory.ObjectProvider;

import java.time.Instant;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

public class ExecuteActuatorUseCase {

    private static final List<String> ACCIONES_VALIDAS = List.of("ON", "OFF");

    private final DispositivoRepositoryPort dispositivoRepositoryPort;
    private final ActuatorEventRepositoryPort actuatorEventRepositoryPort;
    private final ObjectProvider<MqttPublisherPort> mqttPublisherPortProvider;

    public ExecuteActuatorUseCase(
            DispositivoRepositoryPort dispositivoRepositoryPort,
            ActuatorEventRepositoryPort actuatorEventRepositoryPort,
            ObjectProvider<MqttPublisherPort> mqttPublisherPortProvider
    ) {
        this.dispositivoRepositoryPort = dispositivoRepositoryPort;
        this.actuatorEventRepositoryPort = actuatorEventRepositoryPort;
        this.mqttPublisherPortProvider = mqttPublisherPortProvider;
    }

    public ActuatorEvent ejecutar(String deviceId, String actuador, String accion) {
        String normalizedDeviceId = normalizeRequiredValue(deviceId, "El device_id es obligatorio");
        String normalizedActuator = normalizeRequiredValue(actuador, "El actuador es obligatorio");
        String normalizedAction = normalizeRequiredValue(accion, "La accion es obligatoria")
                .toUpperCase(Locale.ROOT);

        if (!ACCIONES_VALIDAS.contains(normalizedAction)) {
            throw new IllegalArgumentException("La accion debe ser ON o OFF");
        }

        Dispositivo dispositivo = dispositivoRepositoryPort.obtenerPorDeviceId(normalizedDeviceId)
                .orElseThrow(() -> new IllegalArgumentException("El device_id no existe"));

        if (!actuatorExists(dispositivo, normalizedActuator)) {
            throw new IllegalArgumentException("El actuador no existe para el device_id indicado");
        }

        String topic = buildTopic(normalizedDeviceId, normalizedActuator);

        MqttPublisherPort mqttPublisherPort = Optional.ofNullable(mqttPublisherPortProvider.getIfAvailable())
                .orElseThrow(() -> new IllegalStateException("El publicador MQTT no esta disponible"));

        mqttPublisherPort.publicar(
                topic,
                Map.of(
                        "device_id", normalizedDeviceId,
                        "actuador", normalizedActuator,
                        "accion", normalizedAction
                )
        );

        ActuatorEvent actuatorEvent = new ActuatorEvent(
                normalizedDeviceId,
                normalizedActuator,
                normalizedAction,
                topic,
                "PUBLISHED",
                Instant.now()
        );

        return actuatorEventRepositoryPort.guardar(actuatorEvent);
    }

    private boolean actuatorExists(Dispositivo dispositivo, String normalizedActuator) {
        List<String> actuadores = Optional.ofNullable(dispositivo.getActuadores())
                .orElse(List.of());

        return actuadores.stream()
                .filter(value -> value != null && !value.isBlank())
                .anyMatch(value -> value.trim().equalsIgnoreCase(normalizedActuator));
    }

    private String buildTopic(String deviceId, String actuador) {
        return "invernadero/" + deviceId + "/actuadores/" + actuador + "/cmd";
    }

    private String normalizeRequiredValue(String value, String errorMessage) {
        String normalizedValue = value == null ? "" : value.trim();

        if (normalizedValue.isBlank()) {
            throw new IllegalArgumentException(errorMessage);
        }

        return normalizedValue;
    }
}