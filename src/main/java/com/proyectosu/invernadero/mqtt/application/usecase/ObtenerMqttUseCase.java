package com.proyectosu.invernadero.mqtt.application.usecase;

import com.proyectosu.invernadero.mqtt.domain.Mqtt;
import com.proyectosu.invernadero.mqtt.domain.ports.MqttRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ObtenerMqttUseCase {

    private final MqttRepositoryPort mqttRepositoryPort;

    public List<Mqtt> ejecutar() {
        return mqttRepositoryPort.obtenerTodos();
    }
}
