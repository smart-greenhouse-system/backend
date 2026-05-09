package com.proyectosu.invernadero.mqtt.application.usecase;

import com.proyectosu.invernadero.mqtt.domain.Mqtt;
import com.proyectosu.invernadero.mqtt.domain.ports.MqttRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GuardarMqttUseCase {

    private final MqttRepositoryPort mqttRepositoryPort;

    public Mqtt ejecutar(String topico, String mensaje) {
        Mqtt mqtt = new Mqtt(topico, mensaje);
        return mqttRepositoryPort.guardar(mqtt);
    }
}
