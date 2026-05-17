package com.proyectosu.invernadero.devices.application.usecase;

import com.proyectosu.invernadero.devices.domain.model.Dispositivo;
import com.proyectosu.invernadero.devices.domain.ports.DispositivoRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ObtenerDispositivosUseCase {

    private final DispositivoRepositoryPort dispositivoRepositoryPort;

    public List<Dispositivo> ejecutar(String deviceId) {
        String normalizedDeviceId = deviceId == null ? "" : deviceId.trim();

        if (normalizedDeviceId.isBlank()) {
            return dispositivoRepositoryPort.listarTodos();
        }

        return dispositivoRepositoryPort.obtenerPorDeviceId(normalizedDeviceId)
                .map(List::of)
                .orElseGet(List::of);
    }
}