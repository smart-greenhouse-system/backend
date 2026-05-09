package com.proyectosu.invernadero.mqtt.domain.ports;

import com.proyectosu.invernadero.mqtt.domain.SensorNodo2;

import java.util.List;

public interface SensorNodo2RepositoryPort {

    SensorNodo2 guardar(SensorNodo2 domain);

    List<SensorNodo2> listarPorDeviceId(String deviceId);
}