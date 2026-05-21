package com.proyectosu.invernadero.prediction.infrastructure.outbound.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoPredictionRepository extends MongoRepository<MongoPredictionDocument, String> {
}
