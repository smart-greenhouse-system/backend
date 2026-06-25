package com.proyectosu.invernadero.actuator.infrastructure.outbound.resolver;

import com.proyectosu.invernadero.actuator.domain.model.Actuator;
import com.proyectosu.invernadero.actuator.domain.model.ActuatorTarget;
import com.proyectosu.invernadero.actuator.domain.port.ActuatorRepositoryPort;
import com.proyectosu.invernadero.actuator.domain.port.ActuatorResolverPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MongoActuatorResolverAdapter implements ActuatorResolverPort {

    private final ActuatorRepositoryPort actuatorRepositoryPort;

    @Override
    public ActuatorTarget resolve(String actuatorId) {
        Actuator actuator = actuatorRepositoryPort.findByActuatorId(actuatorId)
                .orElseThrow(() -> new IllegalArgumentException("Actuador no encontrado: " + actuatorId));

        if (!actuator.isEnabled()) {
            throw new IllegalArgumentException("Actuador deshabilitado: " + actuatorId);
        }

        return new ActuatorTarget(
                actuator.getActuatorId(),
                actuator.getDeviceId(),
                actuator.getActuador()
        );
    }
}
