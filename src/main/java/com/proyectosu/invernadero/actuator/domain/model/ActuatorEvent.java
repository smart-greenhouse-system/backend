package com.proyectosu.invernadero.actuator.domain.model;

import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
public class ActuatorEvent {

    public static final String ORIGIN_IOT = "IOT";
    public static final String EVENT_TYPE_CONFIRMATION = "CONFIRMATION";
    public static final String STATUS_CONFIRMED = "CONFIRMED";

    private final String id;
    private final String deviceId;
    private final String actuator;
    private final String action;
    private final boolean executed;
    private final String origin;
    private final String eventType;
    private final String status;
    private final String topic;
    private final Integer timeAction;
    private final Instant createdAt;

    private ActuatorEvent(
            String id,
            String deviceId,
            String actuator,
            String action,
            boolean executed,
            String origin,
            String eventType,
            String status,
            String topic,
            Integer timeAction,
            Instant createdAt
    ) {
        this.id = id;
        this.deviceId = deviceId;
        this.actuator = actuator;
        this.action = action;
        this.executed = executed;
        this.origin = origin;
        this.eventType = eventType;
        this.status = status;
        this.topic = topic;
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
            String eventType,
            String status,
            String topic,
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
                eventType,
                status,
                topic,
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
                null,
                null,
                null,
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
                null,
                null,
                null,
                Instant.now()
        );
    }

    public static ActuatorEvent fromIotConfirmation(
            String deviceId,
            String actuator,
            String action,
            boolean executed,
            String topic
    ) {
        return new ActuatorEvent(
                UUID.randomUUID().toString(),
                deviceId,
                actuator,
                action,
                executed,
                ORIGIN_IOT,
                EVENT_TYPE_CONFIRMATION,
                STATUS_CONFIRMED,
                topic,
                null,
                Instant.now()
        );
    }
}
