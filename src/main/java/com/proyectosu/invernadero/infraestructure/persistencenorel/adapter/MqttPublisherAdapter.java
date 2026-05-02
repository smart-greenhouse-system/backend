package com.proyectosu.invernadero.infraestructure.persistencenorel.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MqttPublisherAdapter {
    private final MessageChannel mqttOutboundChannel;

    public void publicar(String topic, String mensaje) {

        mqttOutboundChannel.send(
                MessageBuilder.withPayload(mensaje)
                        .setHeader("mqtt_topic", topic)
                        .build()
        );
    }
}
