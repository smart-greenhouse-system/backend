package com.proyectosu.invernadero.shared.infrastructure.mqtt;

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
    //Aqui iran los handlers

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
            //aca se pondran los topics correctos dinamicos
            if (topic.startsWith("invernadero/{device_id}/sensores")) {
                //Se ejecutara el metodo del handler
            } else if (topic.startsWith("invernadero/{device_id}/actuadores/{tipo}/estado")) {
                //Se ejecutara el metodo del handler
            }

        } catch (Exception e) {
            log.error("Error inesperado procesando mensaje en el tópico: {}", topic, e);
        }
    }
    }
