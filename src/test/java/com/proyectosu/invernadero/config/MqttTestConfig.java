package com.proyectosu.invernadero.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;

@TestConfiguration
@Profile("test")
public class MqttTestConfig {

    @Bean
    @Primary
    public MqttPahoMessageDrivenChannelAdapter inbound(
            DefaultMqttPahoClientFactory factory,
            @Value("${mqtt.client-id}") String clientId,
            @Value("${mqtt.topic-in}") String topicIn,
            @Qualifier("mqttInputChannel") MessageChannel mqttInputChannel) {

        MqttPahoMessageDrivenChannelAdapter adapter =
                new MqttPahoMessageDrivenChannelAdapter(clientId + "-sub", factory, topicIn);

        adapter.setAutoStartup(false);
        adapter.setQos(1);
        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setOutputChannel(mqttInputChannel);

        return adapter;
    }
}
