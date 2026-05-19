package com.proyectosu.invernadero.shared.infrastructure.mqtt;

import com.proyectosu.invernadero.shared.infrastructure.mqtt.handler.ActuatorStateMessageHandler;
import com.proyectosu.invernadero.shared.infrastructure.mqtt.handler.CameraImageMessageHandler;
import com.proyectosu.invernadero.shared.infrastructure.mqtt.handler.SensorNodo1MessageHandler;
import com.proyectosu.invernadero.shared.infrastructure.mqtt.handler.SensorNodo2MessageHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@Component
@RequiredArgsConstructor
@Slf4j
public class MqttMessageListener {

    private final ObjectMapper objectMapper;
    private final SensorNodo1MessageHandler sensorNodo1MessageHandler;
    private final SensorNodo2MessageHandler sensorNodo2MessageHandler;
    private final CameraImageMessageHandler cameraImageMessageHandler;
    private final ActuatorStateMessageHandler actuatorStateMessageHandler;

    @ServiceActivator(inputChannel = "mqttInputChannel")
    public void recibirMensaje(Message<?> message) {

        String topic = "desconocido";
        try {
            topic = message.getHeaders().get("mqtt_receivedTopic").toString();
            String payload = message.getPayload().toString();

            JsonNode json;
            try {
                json = objectMapper.readTree(payload);
            } catch (Exception e) {
                log.warn("Mensaje ignorado en el topico [{}]. Payload JSON invalido: {}", topic, payload);
                return;
            }

            if (topic.startsWith("invernadero/nodo1/sensores")) {
                sensorNodo1MessageHandler.handle(topic, json);
            } else if (topic.startsWith("invernadero/nodo2/sensores")) {
                sensorNodo2MessageHandler.handle(topic, json);
            } else if (topic.startsWith("invernadero/cam/imagen")) {
                cameraImageMessageHandler.handle(topic, json);
            } else if (topic.matches("^invernadero/[^/]+/actuadores/[^/]+/estado$")) {
                actuatorStateMessageHandler.handle(topic, json);
            } else {
                log.debug("Topico MQTT ignorado: {}", topic);
            }

        } catch (Exception e) {
            log.error("Error inesperado procesando mensaje en el topico: {}", topic, e);
        }
    }
}
