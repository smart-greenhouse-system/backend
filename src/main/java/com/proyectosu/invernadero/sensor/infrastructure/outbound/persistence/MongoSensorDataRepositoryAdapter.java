package com.proyectosu.invernadero.sensor.infrastructure.outbound.persistence;

import com.proyectosu.invernadero.sensor.domain.model.SensorData;
import com.proyectosu.invernadero.sensor.domain.port.SensorDataRepositoryPort;
import com.proyectosu.invernadero.sensor.infrastructure.outbound.mappers.SensorDataPersistenceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MongoSensorDataRepositoryAdapter implements SensorDataRepositoryPort {

    private final MongoSensorDataRepository mongoSensorDataRepository;
    private final SensorDataPersistenceMapper sensorDataPersistenceMapper;

    @Override
    public SensorData save(SensorData sensorData) {
        MongoSensorDataDocument document = sensorDataPersistenceMapper.toDocument(sensorData);
        MongoSensorDataDocument savedDocument = mongoSensorDataRepository.save(document);
        return sensorDataPersistenceMapper.toDomain(savedDocument);
    }

    @Override
    public Optional<SensorData> findLatest() {
        return mongoSensorDataRepository.findFirstByOrderByCreatedAtDesc()
                .map(sensorDataPersistenceMapper::toDomain);
    }

    @Override
    public List<SensorData> findHistoryByDeviceId(String deviceId) {
        return mongoSensorDataRepository.findByDeviceIdOrderByCreatedAtDesc(deviceId)
                .stream()
                .map(sensorDataPersistenceMapper::toDomain)
                .toList();
    }
}
