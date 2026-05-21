package com.proyectosu.invernadero.greenhouse.infrastructure.outbound.persistence;

import com.proyectosu.invernadero.greenhouse.domain.model.GreenhouseConfig;
import com.proyectosu.invernadero.greenhouse.domain.port.GreenhouseConfigRepositoryPort;
import com.proyectosu.invernadero.greenhouse.infrastructure.outbound.mappers.GreenhouseConfigPersistenceMapper;
import com.proyectosu.invernadero.prediction.domain.port.GreenhouseConfigReaderPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MongoGreenhouseConfigRepositoryAdapter implements GreenhouseConfigRepositoryPort, GreenhouseConfigReaderPort {

    private final MongoGreenhouseConfigRepository mongoGreenhouseConfigRepository;
    private final GreenhouseConfigPersistenceMapper greenhouseConfigPersistenceMapper;


    @Override
    public GreenhouseConfig save(GreenhouseConfig greenhouseConfig) {
        MongoGreenhouseConfigDocument document = greenhouseConfigPersistenceMapper.toDocument(greenhouseConfig);
        MongoGreenhouseConfigDocument savedDocument = mongoGreenhouseConfigRepository.save(document);
        return greenhouseConfigPersistenceMapper.toDomain(savedDocument);
    }

    @Override
    public Optional<GreenhouseConfig> findCurrent() {
        return mongoGreenhouseConfigRepository.findAll()
                .stream()
                .findFirst()
                .map(greenhouseConfigPersistenceMapper::toDomain);
    }

    @Override
    public GreenhouseConfig getCurrentOrCreateDefault() {
        return findCurrent()
                .orElseGet(() -> save(GreenhouseConfig.createDefault()));
    }

    @Override
    public GreenhouseConfig getCurrentConfig() {
        return getCurrentOrCreateDefault();
    }
}
