package com.proyectosu.invernadero.devices.infrastructure.persistence.repository;

import com.proyectosu.invernadero.devices.infrastructure.persistence.document.DispositivoDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface DispositivoMongoRepository extends MongoRepository<DispositivoDocument, String> {

    List<DispositivoDocument> findAllByOrderByDeviceIdAsc();

    Optional<DispositivoDocument> findByDeviceId(String deviceId);
}