package com.proyectosu.invernadero.actuator.infrastructure.outbound.resolver;

import com.proyectosu.invernadero.actuator.domain.model.ActuatorTarget;
import com.proyectosu.invernadero.actuator.domain.port.ActuatorResolverPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class InMemoryActuatorResolverAdapter implements ActuatorResolverPort {

    private final Map<String, ActuatorTarget> actuators = Map.of(
            "24SKF28", new ActuatorTarget("24SKF28", "nodo1", "riego"),
            "VENT001", new ActuatorTarget("VENT001", "nodo2", "ventilacion"),
            "LUZ001", new ActuatorTarget("LUZ001", "nodo3", "iluminacion")
    );

    @Override
    public ActuatorTarget resolve(String actuatorId) {
        ActuatorTarget target = actuators.get(actuatorId);

        if (target == null) {
            throw new IllegalArgumentException("Actuador no encontrado: " + actuatorId);
        }

        return target;
    }
}
