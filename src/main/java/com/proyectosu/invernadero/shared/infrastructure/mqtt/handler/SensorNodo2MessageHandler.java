package com.proyectosu.invernadero.shared.infrastructure.mqtt.handler;

import com.proyectosu.invernadero.evento.application.usecase.GuardarEventoUseCase;
import com.proyectosu.invernadero.mqtt.infrastructure.dto.Nodo2SensoresDto;
import com.proyectosu.invernadero.mqtt.infrastructure.mapper.Nodo2SensoresMapper;
import com.proyectosu.invernadero.mqtt.infrastructure.service.DevicePresenceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tools.jackson.databind.JsonNode;

@RequiredArgsConstructor
@Component
@Slf4j
public class SensorNodo2MessageHandler {

    private final Nodo2SensoresMapper mapper;
    private final GuardarEventoUseCase guardarEventoUseCase;
    private final DevicePresenceService devicePresenceService;

    public void handle(String topic, JsonNode payload) {
        Nodo2SensoresDto dto = mapper.toDto("nodo2", payload);
        devicePresenceService.markOnline(dto.deviceId());

        guardarEventoUseCase.ejecutar(
                dto.deviceId(),
                "MQTT_SENSOR_NODO2",
                "temperatura=%s, iluminacion=%s".formatted(dto.temperatura(), dto.iluminacion())
        );

        log.info("DTO nodo2 procesado: {}", dto);
    }
}
