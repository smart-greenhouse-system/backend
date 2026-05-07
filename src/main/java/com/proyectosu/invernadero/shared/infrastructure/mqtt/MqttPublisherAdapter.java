package com.proyectosu.invernadero.shared.infrastructure.mqtt;

import com.proyectosu.invernadero.shared.domain.ports.MqttPublisherPort;
import lombok.RequiredArgsConstructor;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
@RequiredArgsConstructor
public class MqttPublisherAdapter implements MqttPublisherPort {
    private final MessageChannel mqttOutboundChannel;
    private final ObjectMapper objectMapper;

    @Override
    public void publicar(String topic, Object payload) {
        try {

            String json = objectMapper.writeValueAsString(payload);

            mqttOutboundChannel.send(
                    MessageBuilder.withPayload(json)
                            .setHeader(MqttHeaders.TOPIC, topic)
                            .build()
            );

        } catch (Exception e) {
            throw new RuntimeException("Error publicando mensaje MQTT", e);
        }
    }
}
