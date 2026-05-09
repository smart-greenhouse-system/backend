package com.proyectosu.invernadero.telemetria.application.usecase;

import com.proyectosu.invernadero.telemetria.domain.model.MedicionTelemetria;
import com.proyectosu.invernadero.telemetria.domain.ports.MedicionTelemetriaRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class ObtenerUltimaMedicionUseCase {

    private final MedicionTelemetriaRepositoryPort telemetriaRepositoryPort;

    public Optional<MedicionTelemetria> ejecutar(String deviceId) {
        return telemetriaRepositoryPort.obtenerUltimaMedicion(deviceId);
    }
}