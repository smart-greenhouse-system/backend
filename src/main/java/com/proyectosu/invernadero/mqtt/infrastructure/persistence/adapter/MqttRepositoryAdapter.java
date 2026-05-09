package com.proyectosu.invernadero.mqtt.infrastructure.persistence.adapter;

import com.proyectosu.invernadero.mqtt.domain.Mqtt;
import com.proyectosu.invernadero.mqtt.domain.ports.MqttRepositoryPort;
import com.proyectosu.invernadero.mqtt.infrastructure.persistence.document.MqttDocument;
import com.proyectosu.invernadero.mqtt.infrastructure.persistence.mapper.MqttMapper;
import com.proyectosu.invernadero.mqtt.infrastructure.persistence.repository.MqttMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MqttRepositoryAdapter implements MqttRepositoryPort {

    private final MqttMongoRepository repository;
    private final MqttMapper mapper;

    @Override
    public Mqtt guardar(Mqtt mqtt) {
        MqttDocument document = mapper.toDocument(mqtt);
        return mapper.toDomain(repository.save(document));
    }

    @Override
    public List<Mqtt> obtenerTodos() {
        return repository.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }
}
