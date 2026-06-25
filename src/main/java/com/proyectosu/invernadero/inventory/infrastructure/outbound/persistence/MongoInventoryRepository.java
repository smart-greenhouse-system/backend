package com.proyectosu.invernadero.inventory.infrastructure.outbound.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoInventoryRepository extends MongoRepository<MongoInventoryItemDocument, String> {
}
