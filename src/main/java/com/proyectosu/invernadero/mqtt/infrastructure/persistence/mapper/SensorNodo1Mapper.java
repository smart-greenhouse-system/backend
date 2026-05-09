package com.proyectosu.invernadero.mqtt.infrastructure.persistence.mapper;

import com.proyectosu.invernadero.mqtt.domain.SensorNodo1;
import com.proyectosu.invernadero.mqtt.infrastructure.persistence.document.SensorNodo1Document;
import org.springframework.stereotype.Component;

@Component
public class SensorNodo1Mapper {

    public SensorNodo1 toDomain(SensorNodo1Document document) {
        return new SensorNodo1(
                document.getDeviceId(),
                document.getHumedadRelativa(),
                document.getHumedadSuelo(),
                document.getTimestamp()
        );
    }

    public SensorNodo1Document toDocument(SensorNodo1 domain) {
        return new SensorNodo1Document(
                null,
                domain.getDeviceId(),
                domain.getHumedadRelativa(),
                domain.getHumedadSuelo(),
                domain.getTimestamp()
        );
    }
}
