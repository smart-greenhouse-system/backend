package com.proyectosu.invernadero.image.domain.model;

import lombok.Getter;

import java.time.Instant;

@Getter
public class GreenhouseImage {

    private final String deviceId;
    private final String format;
    private final String resolution;
    private final String imageBase64;
    private final Instant receivedAt;

    private GreenhouseImage(
            String deviceId,
            String format,
            String resolution,
            String imageBase64,
            Instant receivedAt
    ) {
        this.deviceId = deviceId;
        this.format = format;
        this.resolution = resolution;
        this.imageBase64 = imageBase64;
        this.receivedAt = receivedAt;
    }

    public static GreenhouseImage create(
            String deviceId,
            String format,
            String resolution,
            String imageBase64
    ) {
        return new GreenhouseImage(
                deviceId,
                format,
                resolution,
                imageBase64,
                Instant.now()
        );
    }

    public static GreenhouseImage restore(
            String deviceId,
            String format,
            String resolution,
            String imageBase64,
            Instant receivedAt
    ) {
        return new GreenhouseImage(
                deviceId,
                format,
                resolution,
                imageBase64,
                receivedAt
        );
    }
}
