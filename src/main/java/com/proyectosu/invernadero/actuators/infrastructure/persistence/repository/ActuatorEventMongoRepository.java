package com.proyectosu.invernadero.actuators.infrastructure.persistence.repository;

import com.proyectosu.invernadero.actuators.infrastructure.persistence.document.ActuatorEventDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ActuatorEventMongoRepository extends MongoRepository<ActuatorEventDocument, String> {
}