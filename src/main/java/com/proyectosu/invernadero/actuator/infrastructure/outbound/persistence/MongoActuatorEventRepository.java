package com.proyectosu.invernadero.actuator.infrastructure.outbound.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoActuatorEventRepository extends MongoRepository<MongoActuatorEventDocument, String> {
}
