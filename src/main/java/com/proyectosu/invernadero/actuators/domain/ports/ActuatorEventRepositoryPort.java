package com.proyectosu.invernadero.actuators.domain.ports;

import com.proyectosu.invernadero.actuators.domain.model.ActuatorEvent;

public interface ActuatorEventRepositoryPort {

    ActuatorEvent guardar(ActuatorEvent actuatorEvent);
}