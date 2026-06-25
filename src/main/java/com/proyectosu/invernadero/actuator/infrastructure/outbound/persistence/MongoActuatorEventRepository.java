package com.proyectosu.invernadero.actuator.infrastructure.outbound.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MongoActuatorEventRepository extends MongoRepository<MongoActuatorEventDocument, String> {

    List<MongoActuatorEventDocument> findByDeviceIdOrderByCreatedAtDesc(String deviceId);
}
