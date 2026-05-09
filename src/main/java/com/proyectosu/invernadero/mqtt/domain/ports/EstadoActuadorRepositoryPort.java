package com.proyectosu.invernadero.mqtt.domain.ports;

import com.proyectosu.invernadero.mqtt.domain.EstadoActuador;

import java.util.List;

public interface EstadoActuadorRepositoryPort {

    EstadoActuador guardar(EstadoActuador domain);

    List<EstadoActuador> listarPorDeviceId(String deviceId);
}