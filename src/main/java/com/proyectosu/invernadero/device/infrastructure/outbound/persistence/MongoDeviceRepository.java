package com.proyectosu.invernadero.device.infrastructure.outbound.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoDeviceRepository extends MongoRepository<MongoDeviceDocument, String> {
}
