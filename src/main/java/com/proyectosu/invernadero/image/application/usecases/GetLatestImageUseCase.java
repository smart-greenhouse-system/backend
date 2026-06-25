package com.proyectosu.invernadero.image.application.usecases;

import com.proyectosu.invernadero.image.domain.model.GreenhouseImage;
import com.proyectosu.invernadero.image.domain.port.ImageStoragePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetLatestImageUseCase {

    private final ImageStoragePort imageStoragePort;

    public GreenhouseImage execute(String deviceId) {
        if (isBlank(deviceId)) {
            throw new IllegalArgumentException("El deviceId es obligatorio");
        }

        return imageStoragePort.findLatestByDeviceId(deviceId)
                .orElseThrow(() -> new IllegalArgumentException("No hay imagen disponible para el dispositivo"));
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
