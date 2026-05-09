package com.proyectosu.invernadero.telemetria.infrastructure.persistence.repository;

import com.proyectosu.invernadero.telemetria.infrastructure.persistence.document.MedicionTelemetriaDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface MedicionTelemetriaMongoRepository extends MongoRepository<MedicionTelemetriaDocument, String> {

    Optional<MedicionTelemetriaDocument> findFirstByDeviceIdOrderByTimestampDesc(String deviceId);
}