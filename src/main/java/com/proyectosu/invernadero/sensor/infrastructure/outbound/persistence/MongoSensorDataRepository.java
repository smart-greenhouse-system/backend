package com.proyectosu.invernadero.sensor.infrastructure.outbound.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface MongoSensorDataRepository extends MongoRepository<MongoSensorDataDocument, String> {

    Optional<MongoSensorDataDocument> findFirstByOrderByCreatedAtDesc();

    List<MongoSensorDataDocument> findByDeviceIdOrderByCreatedAtDesc(String deviceId);
}
