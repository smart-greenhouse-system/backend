package com.proyectosu.invernadero.telemetria.application.usecase;

import com.proyectosu.invernadero.telemetria.domain.model.MedicionTelemetria;
import com.proyectosu.invernadero.telemetria.domain.ports.MedicionTelemetriaRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.List;

@RequiredArgsConstructor
public class ObtenerHistoricoMedicionesUseCase {

    private final MedicionTelemetriaRepositoryPort telemetriaRepositoryPort;

    public List<MedicionTelemetria> ejecutar(String deviceId, Instant from, Instant to, int limit) {
        return telemetriaRepositoryPort.obtenerHistorico(deviceId, from, to, limit);
    }
}