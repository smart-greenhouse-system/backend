package com.proyectosu.invernadero.mqtt.application.usecase;

import com.proyectosu.invernadero.mqtt.domain.EstadoActuador;
import com.proyectosu.invernadero.mqtt.domain.ports.EstadoActuadorRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GuardarEstadoActuadorUseCase {

    private final EstadoActuadorRepositoryPort repositoryPort;

    public EstadoActuador ejecutar(EstadoActuador domain) {
        return repositoryPort.guardar(domain);
    }
}