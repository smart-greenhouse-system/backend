package com.proyectosu.invernadero.shared.infrastructure.mqtt;

import com.proyectosu.invernadero.shared.infrastructure.mqtt.handler.SensorNodo1MessageHandler;
import com.proyectosu.invernadero.shared.infrastructure.mqtt.handler.SensorNodo2MessageHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Profile;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@Component
@RequiredArgsConstructor
@Profile("!test")
public class MqttMessageListener {

    private final ObjectMapper objectMapper;
    private final SensorNodo2MessageHandler sensorNodo2MessageHandler;
    private final SensorNodo1MessageHandler sensorNodo1MessageHandler;

    @ServiceActivator(inputChannel = "mqttInputChannel")
    public void recibirMensaje(Message<?> message) {

        try {
            String topic = message.getHeaders()
                    .get("mqtt_receivedTopic")
                    .toString();

            String payload = message.getPayload().toString();

            JsonNode json = objectMapper.readTree(payload);

            if (topic.startsWith("invernadero/nodo1/sensores")) {

                sensorNodo1MessageHandler.handle(topic, json);

            } else if (topic.startsWith("invernadero/nodo2/sensores")) {

                sensorNodo2MessageHandler.handle(topic, json);

            }

        } catch (Exception e) {

            throw new RuntimeException("Error procesando mensaje MQTT", e);
        }
    }
}
