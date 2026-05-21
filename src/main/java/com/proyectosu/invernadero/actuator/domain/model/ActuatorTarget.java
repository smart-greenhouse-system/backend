package com.proyectosu.invernadero.actuator.domain.model;

import lombok.Getter;

@Getter
public class ActuatorTarget {
    private final String actuatorId;
    private final String deviceId;
    private final String actuator;

    public ActuatorTarget(String actuatorId, String deviceId, String actuator) {
        this.actuatorId = actuatorId;
        this.deviceId = deviceId;
        this.actuator = actuator;
    }
}
