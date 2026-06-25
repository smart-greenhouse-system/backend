package com.proyectosu.invernadero.image.domain.port;

import com.proyectosu.invernadero.image.domain.model.GreenhouseImage;

import java.util.Optional;

public interface ImageStoragePort {

    void storeLatest(GreenhouseImage image);

    Optional<GreenhouseImage> findLatestByDeviceId(String deviceId);
}
