package com.proyectosu.invernadero.shared.config.mqtt;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;

import javax.net.ssl.SSLSocketFactory;

@Configuration
public class MqttConfig {

    @Value("${mqtt.host}")
    private String host;

    @Value("${mqtt.port}")
    private int port;

    @Value("${mqtt.username}")
    private String username;

    @Value("${mqtt.password}")
    private String password;

    @Value("${mqtt.clientId}")
    private String clientId;

    @Bean
    public MqttConnectOptions mqttConnectOptions() {

        MqttConnectOptions options = new MqttConnectOptions();

        options.setServerURIs(new String[]{host + ":" + port});
        options.setUserName(username);
        options.setPassword(password.toCharArray());
        options.setSocketFactory((SSLSocketFactory) SSLSocketFactory.getDefault());

        options.setAutomaticReconnect(true);
        options.setCleanSession(true);

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


