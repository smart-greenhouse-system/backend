package com.proyectosu.invernadero.mqtt.infrastructure.persistence.mapper;

import com.proyectosu.invernadero.mqtt.domain.SensorNodo2;
import com.proyectosu.invernadero.mqtt.infrastructure.persistence.document.SensorNodo2Document;
import org.springframework.stereotype.Component;

@Component
public class SensorNodo2Mapper {

    public SensorNodo2 toDomain(SensorNodo2Document document) {
        return new SensorNodo2(
                document.getDeviceId(),
                document.getTemperatura(),
                document.getIluminacion(),
                document.getTimestamp()
        );
    }

    public SensorNodo2Document toDocument(SensorNodo2 domain) {
        return new SensorNodo2Document(
                null,
                domain.getDeviceId(),
                domain.getTemperatura(),
                domain.getIluminacion(),
                domain.getTimestamp()
        );
    }
}
