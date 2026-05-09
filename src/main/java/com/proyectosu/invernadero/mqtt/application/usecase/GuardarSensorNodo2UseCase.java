package com.proyectosu.invernadero.mqtt.application.usecase;

import com.proyectosu.invernadero.mqtt.domain.SensorNodo2;
import com.proyectosu.invernadero.mqtt.domain.ports.SensorNodo2RepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GuardarSensorNodo2UseCase {

    private final SensorNodo2RepositoryPort repositoryPort;

    public SensorNodo2 ejecutar(SensorNodo2 domain) {
        return repositoryPort.guardar(domain);
    }
}