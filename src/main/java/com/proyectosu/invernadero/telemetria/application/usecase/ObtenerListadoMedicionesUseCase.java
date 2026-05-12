package com.proyectosu.invernadero.telemetria.application.usecase;

import com.proyectosu.invernadero.telemetria.domain.model.MedicionTelemetria;
import com.proyectosu.invernadero.telemetria.domain.ports.MedicionTelemetriaRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ObtenerListadoMedicionesUseCase {

    private final MedicionTelemetriaRepositoryPort telemetriaRepositoryPort;

    public List<MedicionTelemetria> ejecutar(String deviceId, int limit) {
        return telemetriaRepositoryPort.obtenerListadoPorDispositivo(deviceId, limit);
    }
}