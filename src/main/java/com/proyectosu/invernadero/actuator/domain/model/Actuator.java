package com.proyectosu.invernadero.actuator.domain.model;

import lombok.Getter;

import java.time.Instant;

@Getter
public class Actuator {

    public static final String STATUS_ON = "ON";
    public static final String STATUS_OFF = "OFF";

    private final String actuatorId;
    private final String deviceId;
    private final String actuador;
    private final String nombre;
    private final String estado;
    private final boolean enabled;
    private final Instant createdAt;
    private final Instant updatedAt;

    private Actuator(
            String actuatorId,
            String deviceId,
            String actuador,
            String nombre,
            String estado,
            boolean enabled,
            Instant createdAt,
            Instant updatedAt
    ) {
        this.actuatorId = actuatorId;
        this.deviceId = deviceId;
        this.actuador = actuador;
        this.nombre = nombre;
        this.estado = estado;
        this.enabled = enabled;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Actuator createNew(
            String actuatorId,
            String deviceId,
            String actuador,
            String nombre,
            String estado,
            boolean enabled
    ) {
        Instant now = Instant.now();
        return new Actuator(
                actuatorId,
                deviceId,
                actuador,
                nombre,
                estado != null ? estado : STATUS_OFF,
                enabled,
                now,
                now
        );
    }

    public static Actuator restore(
            String actuatorId,
            String deviceId,
            String actuador,
            String nombre,
            String estado,
            boolean enabled,
            Instant createdAt,
            Instant updatedAt
    ) {
        return new Actuator(
                actuatorId,
                deviceId,
                actuador,
                nombre,
                estado,
                enabled,
                createdAt,
                updatedAt
        );
    }

    public Actuator applyPartialUpdate(
            String deviceId,
            String actuador,
            String nombre,
            String estado,
            Boolean enabled
    ) {
        return Actuator.restore(
                this.actuatorId,
                deviceId != null ? deviceId : this.deviceId,
                actuador != null ? actuador : this.actuador,
                nombre != null ? nombre : this.nombre,
                estado != null ? estado : this.estado,
                enabled != null ? enabled : this.enabled,
                this.createdAt,
                Instant.now()
        );
    }
}
