package com.proyectosu.invernadero.devices.domain.ports;

import com.proyectosu.invernadero.devices.domain.model.Dispositivo;

import java.util.List;
import java.util.Optional;

public interface DispositivoRepositoryPort {

    List<Dispositivo> listarTodos();

    Optional<Dispositivo> obtenerPorDeviceId(String deviceId);

    Dispositivo guardar(Dispositivo dispositivo);
}