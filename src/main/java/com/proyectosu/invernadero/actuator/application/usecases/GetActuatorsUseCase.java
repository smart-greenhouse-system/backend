package com.proyectosu.invernadero.actuator.application.usecases;

import com.proyectosu.invernadero.actuator.domain.model.Actuator;
import com.proyectosu.invernadero.actuator.domain.port.ActuatorRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
public class GetActuatorsUseCase {

    private final ActuatorRepositoryPort actuatorRepositoryPort;

    public List<Actuator> execute() {
        return actuatorRepositoryPort.findAll()
                .stream()
                .sorted(Comparator.comparing(Actuator::getActuatorId, Comparator.nullsLast(String::compareTo)))
                .toList();
    }
}
