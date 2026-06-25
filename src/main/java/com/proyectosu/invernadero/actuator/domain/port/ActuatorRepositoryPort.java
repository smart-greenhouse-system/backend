package com.proyectosu.invernadero.actuator.domain.port;

import com.proyectosu.invernadero.actuator.domain.model.Actuator;

import java.util.List;
import java.util.Optional;

public interface ActuatorRepositoryPort {

    List<Actuator> findAll();

    Optional<Actuator> findByActuatorId(String actuatorId);

    Optional<Actuator> findByDeviceIdAndActuador(String deviceId, String actuador);

    boolean existsByActuatorId(String actuatorId);

    Actuator save(Actuator actuator);

    void deleteByActuatorId(String actuatorId);
}
