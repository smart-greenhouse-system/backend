package com.proyectosu.invernadero.actuator.domain.port;

import com.proyectosu.invernadero.actuator.domain.model.ActuatorEvent;

import java.util.List;

public interface ActuatorEventRepositoryPort {
    ActuatorEvent save(ActuatorEvent actuatorEvent);

    List<ActuatorEvent> findAll();
}
