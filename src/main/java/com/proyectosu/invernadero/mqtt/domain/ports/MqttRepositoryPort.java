package com.proyectosu.invernadero.mqtt.domain.ports;

import com.proyectosu.invernadero.mqtt.domain.Mqtt;

import java.util.List;

public interface MqttRepositoryPort {

    Mqtt guardar(Mqtt mqtt);

    List<Mqtt> obtenerTodos();
}
