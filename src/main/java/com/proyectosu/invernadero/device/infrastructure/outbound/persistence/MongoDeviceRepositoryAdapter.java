package com.proyectosu.invernadero.device.infrastructure.outbound.persistence;

import com.proyectosu.invernadero.device.domain.model.Device;
import com.proyectosu.invernadero.device.domain.port.DeviceRepositoryPort;
import com.proyectosu.invernadero.device.infrastructure.outbound.mappers.DevicePersistenceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MongoDeviceRepositoryAdapter implements DeviceRepositoryPort {

    private final MongoDeviceRepository mongoDeviceRepository;
    private final DevicePersistenceMapper devicePersistenceMapper;

    @Override
    public List<Device> findAll() {
        return mongoDeviceRepository.findAll()
                .stream()
                .map(devicePersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Device> findByDeviceId(String deviceId) {
        return mongoDeviceRepository.findById(deviceId)
                .map(devicePersistenceMapper::toDomain);
    }

    @Override
    public Device save(Device device) {
        MongoDeviceDocument document = devicePersistenceMapper.toDocument(device);
        MongoDeviceDocument savedDocument = mongoDeviceRepository.save(document);
        return devicePersistenceMapper.toDomain(savedDocument);
    }
}
