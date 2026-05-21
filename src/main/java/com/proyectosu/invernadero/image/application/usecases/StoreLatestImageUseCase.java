package com.proyectosu.invernadero.image.application.usecases;

import com.proyectosu.invernadero.image.application.command.StoreImageCommand;
import com.proyectosu.invernadero.image.domain.model.GreenhouseImage;
import com.proyectosu.invernadero.image.domain.port.ImageStoragePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StoreLatestImageUseCase {

    private final ImageStoragePort imageStoragePort;

    public GreenhouseImage execute(StoreImageCommand command) {
        validateCommand(command);

        GreenhouseImage image = GreenhouseImage.create(
                command.getDeviceId(),
                command.getFormat(),
                command.getResolution(),
                command.getImageBase64()
        );

        imageStoragePort.storeLatest(image);

        return image;
    }

    private void validateCommand(StoreImageCommand command) {
        if (command == null) {
            throw new IllegalArgumentException("El comando de imagen no puede ser nulo");
        }

        if (isBlank(command.getDeviceId())) {
            throw new IllegalArgumentException("El deviceId es obligatorio");
        }

        if (isBlank(command.getFormat())) {
            throw new IllegalArgumentException("El formato es obligatorio");
        }

        if (isBlank(command.getResolution())) {
            throw new IllegalArgumentException("La resolución es obligatoria");
        }

        if (isBlank(command.getImageBase64())) {
            throw new IllegalArgumentException("La imagen es obligatoria");
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
