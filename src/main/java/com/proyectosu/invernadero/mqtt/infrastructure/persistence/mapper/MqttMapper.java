package com.proyectosu.invernadero.mqtt.infrastructure.persistence.mapper;

import com.proyectosu.invernadero.mqtt.domain.Mqtt;
import com.proyectosu.invernadero.mqtt.infrastructure.persistence.document.MqttDocument;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MqttMapper {
    MqttDocument toDocument(Mqtt mqtt);

    Mqtt toDomain(MqttDocument document);
}
