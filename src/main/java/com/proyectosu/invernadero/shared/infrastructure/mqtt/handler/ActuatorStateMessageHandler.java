package com.proyectosu.invernadero.shared.infrastructure.mqtt.handler;

import com.proyectosu.invernadero.evento.application.usecase.GuardarEventoUseCase;
import com.proyectosu.invernadero.mqtt.infrastructure.dto.ActuatorStateMessageDto;
import com.proyectosu.invernadero.mqtt.infrastructure.mapper.ActuatorStateMapper;
import com.proyectosu.invernadero.mqtt.infrastructure.service.DevicePresenceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tools.jackson.databind.JsonNode;

@RequiredArgsConstructor
@Component
@Slf4j
public class ActuatorStateMessageHandler {

    private final ActuatorStateMapper mapper;
    private final GuardarEventoUseCase guardarEventoUseCase;
    private final DevicePresenceService devicePresenceService;

    public void handle(String topic, JsonNode payload) {
        ActuatorStateMessageDto dto = mapper.toDto(topic, payload);

        if ("offline".equalsIgnoreCase(dto.estado())) {
            devicePresenceService.markOffline(dto.deviceId());
        } else {
            devicePresenceService.markOnline(dto.deviceId());
        }

        guardarEventoUseCase.ejecutar(
                dto.deviceId(),
                "MQTT_ACTUADOR_ESTADO",
                "actuador=%s, estado=%s".formatted(dto.actuator(), dto.estado())
        );

        log.info("DTO estado actuador procesado: {}", dto);
    }
}
