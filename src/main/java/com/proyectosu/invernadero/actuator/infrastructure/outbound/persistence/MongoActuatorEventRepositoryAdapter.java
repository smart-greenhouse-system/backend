package com.proyectosu.invernadero.actuator.infrastructure.outbound.persistence;

import com.proyectosu.invernadero.actuator.domain.model.ActuatorEvent;
import com.proyectosu.invernadero.actuator.domain.port.ActuatorEventRepositoryPort;
import com.proyectosu.invernadero.actuator.infrastructure.outbound.mappers.ActuatorEventPersistenceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MongoActuatorEventRepositoryAdapter implements ActuatorEventRepositoryPort {

    private final MongoActuatorEventRepository mongoActuatorEventRepository;
    private final ActuatorEventPersistenceMapper actuatorEventPersistenceMapper;

    @Override
    public ActuatorEvent save(ActuatorEvent actuatorEvent) {
        MongoActuatorEventDocument document = actuatorEventPersistenceMapper.toDocument(actuatorEvent);
        MongoActuatorEventDocument savedDocument = mongoActuatorEventRepository.save(document);
        return actuatorEventPersistenceMapper.toDomain(savedDocument);
    }

    @Override
    public List<ActuatorEvent> findAll() {
        return mongoActuatorEventRepository.findAll()
                .stream()
                .map(actuatorEventPersistenceMapper::toDomain)
                .toList();
    }
}
