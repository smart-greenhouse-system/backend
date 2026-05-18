package com.proyectosu.invernadero.shared.config.mqtt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;

import java.util.Arrays;

@Configuration
@EnableIntegration
public class MqttSubscriberConfig {

    @Value("${mqtt.client-id}")
    private String clientId;

    @Value("${mqtt.topics-in:invernadero/#}")
    private String[] topicsIn;

    @Bean
    public MessageChannel mqttInputChannel() {
        return new DirectChannel();
    }

    @Bean
    public  MqttPahoMessageDrivenChannelAdapter inbound(DefaultMqttPahoClientFactory factory) {

        MqttPahoMessageDrivenChannelAdapter adapter =
                new MqttPahoMessageDrivenChannelAdapter(
                        clientId + "-sub",
                        factory,
                        topicsIn
                );

        adapter.setAutoStartup(true);
        int[] qosByTopic = Arrays.stream(topicsIn)
                .mapToInt(this::resolveQosForTopic)
                .toArray();
        adapter.setQos(qosByTopic);
        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setOutputChannel(mqttInputChannel());

        return adapter;
    }

    private int resolveQosForTopic(String topic) {
        return topic.endsWith("/sensores") ? 0 : 1;
    }

}
