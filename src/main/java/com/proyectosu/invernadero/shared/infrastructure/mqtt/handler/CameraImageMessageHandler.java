package com.proyectosu.invernadero.shared.infrastructure.mqtt.handler;

import com.proyectosu.invernadero.evento.application.usecase.GuardarEventoUseCase;
import com.proyectosu.invernadero.mqtt.infrastructure.dto.CameraImageMessageDto;
import com.proyectosu.invernadero.mqtt.infrastructure.mapper.CameraImageMapper;
import com.proyectosu.invernadero.mqtt.infrastructure.service.DevicePresenceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tools.jackson.databind.JsonNode;

@RequiredArgsConstructor
@Component
@Slf4j
public class CameraImageMessageHandler {

    private final CameraImageMapper mapper;
    private final GuardarEventoUseCase guardarEventoUseCase;
    private final DevicePresenceService devicePresenceService;

    public void handle(String topic, JsonNode payload) {
        CameraImageMessageDto dto = mapper.toDto(payload);
        devicePresenceService.markOnline(dto.deviceId());

        guardarEventoUseCase.ejecutar(
                dto.deviceId(),
                "MQTT_CAM_IMAGEN",
                "imagen_base64_size=%s".formatted(dto.imageBase64().length())
        );

        log.info("DTO camara procesado: deviceId={} size={}", dto.deviceId(), dto.imageBase64().length());
    }
}
