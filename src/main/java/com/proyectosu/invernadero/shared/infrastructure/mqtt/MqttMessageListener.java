package com.proyectosu.invernadero.shared.infrastructure.mqtt;

import com.proyectosu.invernadero.mqtt.infrastructure.dto.ActuatorStateMessageDto;
import com.proyectosu.invernadero.mqtt.infrastructure.dto.CameraImageMessageDto;
import com.proyectosu.invernadero.mqtt.infrastructure.dto.SensorReadingDto;
import com.proyectosu.invernadero.mqtt.infrastructure.service.DevicePresenceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.time.Instant;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class MqttMessageListener {

    private final ObjectMapper objectMapper;
    private final DevicePresenceService devicePresenceService;

    @ServiceActivator(inputChannel = "mqttInputChannel")
    public void recibirMensaje(Message<?> message) {
        String topic = "desconocido";
        String payload = "desconocido";
        try {
            topic = message.getHeaders()
                    .get("mqtt_receivedTopic")
                    .toString();
            payload = message.getPayload().toString();
            String[] parts = topic.split("/");

            switch (resolveRoute(topic, parts)) {
                case "SENSOR" -> processSensorMessage(parts, payload);
                case "CAMERA_IMAGE" -> processCameraImage(payload);
                case "ACTUATOR_STATE" -> processActuatorState(parts, payload);
                default -> log.debug("Topico MQTT ignorado: {}", topic);
            }
        } catch (Exception e) {
            log.warn("Mensaje MQTT descartado topic={} payload={} motivo={}", topic, payload, e.getMessage());
        }
    }

    private String resolveRoute(String topic, String[] parts) {
        if ("invernadero/cam/imagen".equals(topic)) {
            return "CAMERA_IMAGE";
        }
        if (parts.length == 3 && "invernadero".equals(parts[0]) && "sensores".equals(parts[2])) {
            return "SENSOR";
        }
        if (parts.length == 5 && "invernadero".equals(parts[0]) && "actuadores".equals(parts[2]) && "estado".equals(parts[4])) {
            return "ACTUATOR_STATE";
        }
        return "UNKNOWN";
    }

    private void processSensorMessage(String[] parts, String payload) throws Exception {
        String deviceId = parts[1];
        JsonNode json = objectMapper.readTree(payload);

        JsonNode sensoresNode = json.has("sensores") ? json.get("sensores") : json;
        if (!sensoresNode.isObject()) {
            throw new IllegalArgumentException("Payload de sensores invalido: 'sensores' debe ser objeto");
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> sensores = objectMapper.convertValue(sensoresNode, Map.class);
        SensorReadingDto dto = new SensorReadingDto(deviceId, sensores, Instant.now());
        devicePresenceService.markOnline(deviceId);
        log.info("Sensor DTO recibido {} estado={}", dto, devicePresenceService.getCurrentStatus(deviceId));
    }

    private void processCameraImage(String payload) throws Exception {
        JsonNode json = objectMapper.readTree(payload);
        String imageBase64 = json.hasNonNull("imagen") ? json.get("imagen").asText() : payload;

        if (imageBase64 == null || imageBase64.isBlank()) {
            throw new IllegalArgumentException("Payload de camara invalido: imagen vacia");
        }

        CameraImageMessageDto dto = new CameraImageMessageDto("cam", imageBase64, Instant.now());
        devicePresenceService.markOnline(dto.deviceId());
        log.info("Imagen MQTT recibida deviceId={} size={}", dto.deviceId(), dto.imageBase64().length());
    }

    private void processActuatorState(String[] parts, String payload) throws Exception {
        String deviceId = parts[1];
        String actuator = parts[3];
        JsonNode json = objectMapper.readTree(payload);

        String estado = json.hasNonNull("estado")
                ? json.get("estado").asText()
                : payload;

        if (estado == null || estado.isBlank()) {
            throw new IllegalArgumentException("Payload de estado de actuador invalido: estado vacio");
        }

        ActuatorStateMessageDto dto = new ActuatorStateMessageDto(deviceId, actuator, estado, Instant.now());
        if ("offline".equalsIgnoreCase(dto.estado())) {
            devicePresenceService.markOffline(dto.deviceId());
        } else {
            devicePresenceService.markOnline(dto.deviceId());
        }
        log.info("Estado actuador recibido {}", dto);
    }
}
