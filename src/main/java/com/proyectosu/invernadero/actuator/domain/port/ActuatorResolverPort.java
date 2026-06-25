package com.proyectosu.invernadero.actuator.domain.port;

import com.proyectosu.invernadero.actuator.domain.model.ActuatorTarget;

public interface ActuatorResolverPort {
    ActuatorTarget resolve(String actuatorId);
}
