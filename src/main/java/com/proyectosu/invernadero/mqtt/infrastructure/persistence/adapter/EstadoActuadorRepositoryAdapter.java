package com.proyectosu.invernadero.mqtt.infrastructure.persistence.adapter;

import com.proyectosu.invernadero.mqtt.domain.EstadoActuador;
import com.proyectosu.invernadero.mqtt.domain.ports.EstadoActuadorRepositoryPort;
import com.proyectosu.invernadero.mqtt.infrastructure.persistence.mapper.EstadoActuadorMapper;
import com.proyectosu.invernadero.mqtt.infrastructure.persistence.repository.EstadoActuadorMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class EstadoActuadorRepositoryAdapter implements EstadoActuadorRepositoryPort {

    private final EstadoActuadorMongoRepository repository;
    private final EstadoActuadorMapper mapper;

    @Override
    public EstadoActuador guardar(EstadoActuador domain) {
        return mapper.toDomain(repository.save(mapper.toDocument(domain)));
    }

    @Override
    public List<EstadoActuador> listarPorDeviceId(String deviceId) {
        return repository.findByDeviceId(deviceId)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }
}