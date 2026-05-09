package com.proyectosu.invernadero.telemetria.domain.ports;

import com.proyectosu.invernadero.telemetria.domain.model.MedicionTelemetria;

import java.util.Optional;

public interface MedicionTelemetriaRepositoryPort {

    Optional<MedicionTelemetria> obtenerUltimaMedicion(String deviceId);
}