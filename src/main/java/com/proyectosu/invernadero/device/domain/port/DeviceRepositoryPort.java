package com.proyectosu.invernadero.device.domain.port;

import com.proyectosu.invernadero.device.domain.model.Device;

import java.util.List;
import java.util.Optional;

public interface DeviceRepositoryPort {

    List<Device> findAll();

    Optional<Device> findByDeviceId(String deviceId);

    Device save(Device device);
}
