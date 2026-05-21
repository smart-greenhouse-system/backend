package com.proyectosu.invernadero.shared.infrastructure.mqtt;

import com.proyectosu.invernadero.sensor.infrastructure.inbound.mqtt.SensorMessageHandler;
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
    private final SensorMessageHandler sensorMessageHandler;

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
                log.warn("Mensaje ignorado en el tópico [{}]. El payload no es un JSON válido: {}", topic, payload);
                return;
            }

            if (sensorMessageHandler.supports(topic)) {
                sensorMessageHandler.handle(topic, json);
            } else if (topic.matches("invernadero/[^/]+/actuadores/[^/]+/estado")) {
                //Se ejecutara el metodo del handler de actuadores
            }

        } catch (Exception e) {
            log.error("Error inesperado procesando mensaje en el tópico: {}", topic, e);
        }
    }
}
