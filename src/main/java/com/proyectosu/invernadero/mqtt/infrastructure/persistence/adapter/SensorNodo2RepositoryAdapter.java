package com.proyectosu.invernadero.mqtt.infrastructure.persistence.adapter;

import com.proyectosu.invernadero.mqtt.domain.SensorNodo2;
import com.proyectosu.invernadero.mqtt.domain.ports.SensorNodo2RepositoryPort;
import com.proyectosu.invernadero.mqtt.infrastructure.persistence.mapper.SensorNodo2Mapper;
import com.proyectosu.invernadero.mqtt.infrastructure.persistence.repository.SensorNodo2MongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SensorNodo2RepositoryAdapter implements SensorNodo2RepositoryPort {

    private final SensorNodo2MongoRepository repository;
    private final SensorNodo2Mapper mapper;

    @Override
    public SensorNodo2 guardar(SensorNodo2 domain) {
        return mapper.toDomain(repository.save(mapper.toDocument(domain)));
    }

    @Override
    public List<SensorNodo2> listarPorDeviceId(String deviceId) {
        return repository.findByDeviceId(deviceId)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }
}