package com.proyectosu.invernadero.actuator.infrastructure.outbound.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface MongoActuatorRepository extends MongoRepository<MongoActuatorDocument, String> {

    Optional<MongoActuatorDocument> findByDeviceIdAndActuador(String deviceId, String actuador);
}
