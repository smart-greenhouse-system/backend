package com.proyectosu.invernadero.actuator.domain.model;

import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
public class ActuatorEvent {
    private final String id;
    private final String deviceId;
    private final String actuator;
    private final String action;
    private final boolean executed;
    private final String origin;
    private final Integer timeAction;
    private final Instant createdAt;

    private ActuatorEvent(
            String id,
            String deviceId,
            String actuator,
            String action,
            boolean executed,
            String origin,
            Integer timeAction,
            Instant createdAt
    ) {
        this.id = id;
        this.deviceId = deviceId;
        this.actuator = actuator;
        this.action = action;
        this.executed = executed;
        this.origin = origin;
        this.timeAction = timeAction;
        this.createdAt = createdAt;
    }

    public static ActuatorEvent restore(
            String id,
            String deviceId,
            String actuator,
            String action,
            boolean executed,
            String origin,
            Integer timeAction,
            Instant createdAt
    ) {
        return new ActuatorEvent(
                id,
                deviceId,
                actuator,
                action,
                executed,
                origin,
                timeAction,
                createdAt
        );
    }

    public static ActuatorEvent fromIa(
            String deviceId,
            String actuator,
            String action,
            Integer timeAction
    ) {
        return new ActuatorEvent(
                UUID.randomUUID().toString(),
                deviceId,
                actuator,
                action,
                true,
                "IA",
                timeAction,
                Instant.now()
        );
    }

    public static ActuatorEvent fromManual(
            String deviceId,
            String actuator,
            String action
    ) {
        return new ActuatorEvent(
                UUID.randomUUID().toString(),
                deviceId,
                actuator,
                action,
                true,
                "MANUAL",
                null,
                Instant.now()
        );
    }
}
