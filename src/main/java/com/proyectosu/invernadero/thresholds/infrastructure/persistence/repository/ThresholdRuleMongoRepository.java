package com.proyectosu.invernadero.thresholds.infrastructure.persistence.repository;

import com.proyectosu.invernadero.thresholds.infrastructure.persistence.document.ThresholdRuleDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ThresholdRuleMongoRepository extends MongoRepository<ThresholdRuleDocument, String> {

    Optional<ThresholdRuleDocument> findByGreenhouseIdAndVariable(String greenhouseId, String variable);
}