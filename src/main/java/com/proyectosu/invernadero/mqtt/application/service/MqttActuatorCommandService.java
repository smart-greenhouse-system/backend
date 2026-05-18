package com.proyectosu.invernadero.mqtt.application.service;

import com.proyectosu.invernadero.shared.domain.ports.MqttPublisherPort;
import com.proyectosu.invernadero.mqtt.infrastructure.dto.ActuatorCommandDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MqttActuatorCommandService {

    private final MqttPublisherPort mqttPublisherPort;

    public void publishActuatorCommand(String deviceId, String actuator, ActuatorCommandDto command) {
        if (deviceId == null || deviceId.isBlank()) {
            throw new IllegalArgumentException("deviceId es obligatorio");
        }
        if (actuator == null || actuator.isBlank()) {
            throw new IllegalArgumentException("actuator es obligatorio");
        }
        if (command == null || command.comando() == null || command.comando().isBlank()) {
            throw new IllegalArgumentException("comando es obligatorio");
        }
        String topic = "invernadero/%s/actuadores/%s/cmd".formatted(deviceId, actuator);
        mqttPublisherPort.publicar(topic, command);
    }
}
