package com.proyectosu.invernadero.device.application.usecases;

import com.proyectosu.invernadero.device.application.command.UpsertDeviceStatusCommand;
import com.proyectosu.invernadero.device.domain.model.Device;
import com.proyectosu.invernadero.device.domain.port.DeviceRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class UpsertDeviceStatusUseCase {

    private final DeviceRepositoryPort deviceRepositoryPort;

    public Device execute(UpsertDeviceStatusCommand command) {
        validateCommand(command);

        Instant now = Instant.now();
        List<String> sensores = command.getSensores() != null
                ? new ArrayList<>(command.getSensores())
                : List.of();

        Device device = deviceRepositoryPort.findByDeviceId(command.getDeviceId())
                .map(existing -> existing.markOnline(sensores, now))
                .orElseGet(() -> Device.createNew(command.getDeviceId(), sensores, now));

        return deviceRepositoryPort.save(device);
    }

    private void validateCommand(UpsertDeviceStatusCommand command) {
        if (command == null) {
            throw new IllegalArgumentException("El comando del dispositivo no puede ser nulo");
        }

        if (isBlank(command.getDeviceId())) {
            throw new IllegalArgumentException("El deviceId es obligatorio");
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
