package com.proyectosu.invernadero.mqtt.infrastructure.persistence.repository;

import com.proyectosu.invernadero.mqtt.infrastructure.persistence.document.MqttDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MqttMongoRepository extends MongoRepository<MqttDocument, String> {
}
