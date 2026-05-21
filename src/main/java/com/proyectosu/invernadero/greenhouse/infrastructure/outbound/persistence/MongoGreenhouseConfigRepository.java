package com.proyectosu.invernadero.greenhouse.infrastructure.outbound.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoGreenhouseConfigRepository extends MongoRepository<MongoGreenhouseConfigDocument, String> {
}
