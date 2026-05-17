package com.proyectosu.invernadero.telemetria.infrastructure.persistence.adapter;

import com.proyectosu.invernadero.telemetria.domain.model.MedicionTelemetria;
import com.proyectosu.invernadero.telemetria.domain.ports.MedicionTelemetriaRepositoryPort;
import com.proyectosu.invernadero.telemetria.infrastructure.persistence.mapper.MedicionTelemetriaMapper;
import com.proyectosu.invernadero.telemetria.infrastructure.persistence.repository.MedicionTelemetriaMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MedicionTelemetriaRepositoryAdapter implements MedicionTelemetriaRepositoryPort {

    private final MedicionTelemetriaMongoRepository repository;
    private final MedicionTelemetriaMapper mapper;

    @Override
    public MedicionTelemetria guardar(MedicionTelemetria medicion) {
        return mapper.toDomain(repository.save(mapper.toDocument(medicion)));
    }

    @Override
    public Optional<MedicionTelemetria> obtenerUltimaMedicion(String deviceId) {
        return repository.findFirstByDeviceIdOrderByTimestampDesc(deviceId)
                .map(mapper::toDomain);
    }

    @Override
    public List<MedicionTelemetria> obtenerHistorico(String deviceId, Instant from, Instant to, int limit) {
        return repository.findByDeviceIdOrderByTimestampAsc(deviceId)
                .stream()
                .filter(document -> from == null || !document.getTimestamp().isBefore(from))
                .filter(document -> to == null || !document.getTimestamp().isAfter(to))
                .limit(limit)
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<MedicionTelemetria> obtenerListadoPorDispositivo(String deviceId, int limit) {
        return repository.findByDeviceIdOrderByTimestampDesc(deviceId)
                .stream()
                .limit(limit)
                .map(mapper::toDomain)
                .toList();
    }
}