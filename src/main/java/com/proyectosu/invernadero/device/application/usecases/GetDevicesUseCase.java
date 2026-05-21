package com.proyectosu.invernadero.device.application.usecases;

import com.proyectosu.invernadero.device.domain.model.Device;
import com.proyectosu.invernadero.device.domain.port.DeviceRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
public class GetDevicesUseCase {

    private final DeviceRepositoryPort deviceRepositoryPort;

    public List<Device> execute() {
        return deviceRepositoryPort.findAll()
                .stream()
                .sorted(Comparator.comparing(Device::getDeviceId))
                .toList();
    }
}
