package com.proyectosu.invernadero.actuators.infrastructure.persistence.adapter;

import com.proyectosu.invernadero.actuators.domain.model.ActuatorEvent;
import com.proyectosu.invernadero.actuators.domain.ports.ActuatorEventRepositoryPort;
import com.proyectosu.invernadero.actuators.infrastructure.persistence.document.ActuatorEventDocument;
import com.proyectosu.invernadero.actuators.infrastructure.persistence.mapper.ActuatorEventMapper;
import com.proyectosu.invernadero.actuators.infrastructure.persistence.repository.ActuatorEventMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ActuatorEventRepositoryAdapter implements ActuatorEventRepositoryPort {

    private final ActuatorEventMongoRepository repository;
    private final ActuatorEventMapper mapper;

    @Override
    public ActuatorEvent guardar(ActuatorEvent actuatorEvent) {
        ActuatorEventDocument document = repository.save(mapper.toDocument(actuatorEvent));
        return mapper.toDomain(document);
    }
}