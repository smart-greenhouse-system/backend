package com.proyectosu.invernadero.telemetria.infrastructure.persistence.adapter;

import com.proyectosu.invernadero.telemetria.domain.model.MedicionTelemetria;
import com.proyectosu.invernadero.telemetria.domain.ports.MedicionTelemetriaRepositoryPort;
import com.proyectosu.invernadero.telemetria.infrastructure.persistence.mapper.MedicionTelemetriaMapper;
import com.proyectosu.invernadero.telemetria.infrastructure.persistence.repository.MedicionTelemetriaMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MedicionTelemetriaRepositoryAdapter implements MedicionTelemetriaRepositoryPort {

    private final MedicionTelemetriaMongoRepository repository;
    private final MedicionTelemetriaMapper mapper;

    @Override
    public Optional<MedicionTelemetria> obtenerUltimaMedicion(String deviceId) {
        return repository.findFirstByDeviceIdOrderByTimestampDesc(deviceId)
                .map(mapper::toDomain);
    }
}