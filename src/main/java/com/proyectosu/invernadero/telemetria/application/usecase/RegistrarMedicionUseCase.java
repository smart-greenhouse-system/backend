package com.proyectosu.invernadero.telemetria.application.usecase;

import com.proyectosu.invernadero.telemetria.domain.model.MedicionTelemetria;
import com.proyectosu.invernadero.telemetria.domain.ports.MedicionTelemetriaRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RegistrarMedicionUseCase {

    private final MedicionTelemetriaRepositoryPort telemetriaRepositoryPort;

    public MedicionTelemetria ejecutar(MedicionTelemetria medicion) {
        if (medicion == null) {
            throw new IllegalArgumentException("La medicion es obligatoria");
        }

        return telemetriaRepositoryPort.guardar(medicion);
    }
}