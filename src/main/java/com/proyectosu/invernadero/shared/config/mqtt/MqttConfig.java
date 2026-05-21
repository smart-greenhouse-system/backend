package com.proyectosu.invernadero.shared.config.mqtt;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;

@Configuration
public class MqttConfig {

    @Value("${mqtt.url}")
    private String brokerUrl;
    @Value("${mqtt.username}")
    private String username;

    @Value("${mqtt.password}")
    private String password;

    @Bean
    public MqttConnectOptions mqttConnectOptions() {

        MqttConnectOptions options = new MqttConnectOptions();

        options.setServerURIs(new String[]{brokerUrl});
        options.setUserName(username);
        options.setPassword(password.toCharArray());

        options.setAutomaticReconnect(true);
        options.setCleanSession(true);
        options.setConnectionTimeout(10);

        return options;
    }

    @Bean
    public DefaultMqttPahoClientFactory mqttClientFactory() {

        DefaultMqttPahoClientFactory factory =
                new DefaultMqttPahoClientFactory();

        factory.setConnectionOptions(mqttConnectOptions());

        return factory;
    }
}


