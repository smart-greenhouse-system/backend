package com.proyectosu.invernadero.shared.config.mqtt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

@Configuration
@EnableIntegration
public class MqttPublisherConfig {
    @Value("${mqtt.client-id}")
    private String clientId;

    @Bean
    public MessageChannel mqttOutboundChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttOutboundChannel")
    public MessageHandler mqttOutbound(
            DefaultMqttPahoClientFactory factory) {

        MqttPahoMessageHandler handler =
                new MqttPahoMessageHandler(
                        clientId + "-pub",
                        factory
                );

        handler.setAsync(true);
        handler.setDefaultQos(1);

        return handler;
    }
}
