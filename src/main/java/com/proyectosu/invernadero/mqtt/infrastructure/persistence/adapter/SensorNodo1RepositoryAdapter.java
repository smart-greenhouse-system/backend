package com.proyectosu.invernadero.mqtt.infrastructure.persistence.adapter;

import com.proyectosu.invernadero.mqtt.domain.SensorNodo1;
import com.proyectosu.invernadero.mqtt.domain.ports.SensorNodo1RepositoryPort;
import com.proyectosu.invernadero.mqtt.infrastructure.persistence.mapper.SensorNodo1Mapper;
import com.proyectosu.invernadero.mqtt.infrastructure.persistence.repository.SensorNodo1MongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SensorNodo1RepositoryAdapter implements SensorNodo1RepositoryPort {

    private final SensorNodo1MongoRepository repository;
    private final SensorNodo1Mapper mapper;

    @Override
    public SensorNodo1 guardar(SensorNodo1 domain) {
        return mapper.toDomain(repository.save(mapper.toDocument(domain)));
    }

    @Override
    public List<SensorNodo1> listarPorDeviceId(String deviceId) {
        return repository.findByDeviceId(deviceId)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }
}