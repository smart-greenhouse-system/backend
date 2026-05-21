package com.proyectosu.invernadero.device.domain.model;

import lombok.Getter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

@Getter
public class Device {

    public static final String STATUS_ONLINE = "ONLINE";

    private final String deviceId;
    private final String nombre;
    private final String tipo;
    private final String estado;
    private final List<String> sensores;
    private final List<String> actuadores;
    private final Instant lastSeen;
    private final Instant createdAt;

    private Device(
            String deviceId,
            String nombre,
            String tipo,
            String estado,
            List<String> sensores,
            List<String> actuadores,
            Instant lastSeen,
            Instant createdAt
    ) {
        this.deviceId = deviceId;
        this.nombre = nombre;
        this.tipo = tipo;
        this.estado = estado;
        this.sensores = List.copyOf(sensores);
        this.actuadores = List.copyOf(actuadores);
        this.lastSeen = lastSeen;
        this.createdAt = createdAt;
    }

    public static Device createNew(String deviceId, List<String> sensores, Instant now) {
        return new Device(
                deviceId,
                "Dispositivo " + deviceId,
                "ESP32",
                STATUS_ONLINE,
                sensores != null ? sensores : List.of(),
                List.of(),
                now,
                now
        );
    }

    public static Device restore(
            String deviceId,
            String nombre,
            String tipo,
            String estado,
            List<String> sensores,
            List<String> actuadores,
            Instant lastSeen,
            Instant createdAt
    ) {
        return new Device(
                deviceId,
                nombre,
                tipo,
                estado,
                sensores != null ? sensores : List.of(),
                actuadores != null ? actuadores : List.of(),
                lastSeen,
                createdAt
        );
    }

    public Device markOnline(List<String> reportedSensores, Instant lastSeen) {
        return Device.restore(
                this.deviceId,
                this.nombre,
                this.tipo,
                STATUS_ONLINE,
                mergeSensores(this.sensores, reportedSensores),
                this.actuadores,
                lastSeen,
                this.createdAt
        );
    }

    private static List<String> mergeSensores(List<String> currentSensores, List<String> reportedSensores) {
        LinkedHashSet<String> merged = new LinkedHashSet<>();

        if (currentSensores != null) {
            merged.addAll(currentSensores);
        }

        if (reportedSensores != null) {
            merged.addAll(reportedSensores);
        }

        return new ArrayList<>(merged);
    }
}
