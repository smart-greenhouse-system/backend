package com.proyectosu.invernadero.shared.mqtt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.messaging.MessageChannel;

@Configuration
public class MqttSubscriberConfig {

    @Value("${mqtt.client-id}")
    private String clientId;

    @Value("${mqtt.topic-in}")
    private String topicIn;

    @Bean
    public MessageChannel mqttInputChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageProducer inbound(DefaultMqttPahoClientFactory factory) {

        MqttPahoMessageDrivenChannelAdapter adapter =
                new MqttPahoMessageDrivenChannelAdapter(
                        clientId + "-sub",
                        factory,
                        topicIn
                );

        adapter.setQos(1);
        adapter.setCompletionTimeout(5000);
        adapter.setOutputChannel(mqttInputChannel());

        return adapter;
    }
}
