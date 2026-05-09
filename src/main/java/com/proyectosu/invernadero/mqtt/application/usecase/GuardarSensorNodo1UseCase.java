package com.proyectosu.invernadero.mqtt.application.usecase;

import com.proyectosu.invernadero.mqtt.domain.SensorNodo1;
import com.proyectosu.invernadero.mqtt.domain.ports.SensorNodo1RepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GuardarSensorNodo1UseCase {

    private final SensorNodo1RepositoryPort repositoryPort;

    public SensorNodo1 ejecutar(SensorNodo1 domain) {
        return repositoryPort.guardar(domain);
    }
}