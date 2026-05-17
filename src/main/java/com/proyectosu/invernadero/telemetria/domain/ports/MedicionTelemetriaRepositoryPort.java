package com.proyectosu.invernadero.telemetria.domain.ports;

import com.proyectosu.invernadero.telemetria.domain.model.MedicionTelemetria;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface MedicionTelemetriaRepositoryPort {

    MedicionTelemetria guardar(MedicionTelemetria medicion);

    Optional<MedicionTelemetria> obtenerUltimaMedicion(String deviceId);

    List<MedicionTelemetria> obtenerHistorico(
            String deviceId,
            Instant from,
            Instant to,
            int limit
    );

    List<MedicionTelemetria> obtenerListadoPorDispositivo(String deviceId, int limit);
}