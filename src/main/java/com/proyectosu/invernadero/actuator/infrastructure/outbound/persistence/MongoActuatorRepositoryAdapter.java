package com.proyectosu.invernadero.actuator.infrastructure.outbound.persistence;

import com.proyectosu.invernadero.actuator.domain.model.Actuator;
import com.proyectosu.invernadero.actuator.domain.port.ActuatorRepositoryPort;
import com.proyectosu.invernadero.actuator.infrastructure.outbound.mappers.ActuatorPersistenceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MongoActuatorRepositoryAdapter implements ActuatorRepositoryPort {

    private final MongoActuatorRepository mongoActuatorRepository;
    private final ActuatorPersistenceMapper actuatorPersistenceMapper;

    @Override
    public List<Actuator> findAll() {
        return mongoActuatorRepository.findAll()
                .stream()
                .map(actuatorPersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Actuator> findByActuatorId(String actuatorId) {
        return mongoActuatorRepository.findById(actuatorId)
                .map(actuatorPersistenceMapper::toDomain);
    }

    @Override
    public boolean existsByActuatorId(String actuatorId) {
        return mongoActuatorRepository.existsById(actuatorId);
    }

    @Override
    public Actuator save(Actuator actuator) {
        MongoActuatorDocument document = actuatorPersistenceMapper.toDocument(actuator);
        MongoActuatorDocument savedDocument = mongoActuatorRepository.save(document);
        return actuatorPersistenceMapper.toDomain(savedDocument);
    }

    @Override
    public void deleteByActuatorId(String actuatorId) {
        mongoActuatorRepository.deleteById(actuatorId);
    }
}
