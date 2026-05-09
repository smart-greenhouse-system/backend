package com.proyectosu.invernadero.mqtt.domain.ports;

import com.proyectosu.invernadero.mqtt.domain.SensorNodo1;

import java.util.List;

public interface SensorNodo1RepositoryPort {

    SensorNodo1 guardar(SensorNodo1 domain);

    List<SensorNodo1> listarPorDeviceId(String deviceId);
}