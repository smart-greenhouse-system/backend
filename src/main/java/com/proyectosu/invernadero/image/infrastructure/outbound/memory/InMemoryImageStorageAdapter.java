package com.proyectosu.invernadero.image.infrastructure.outbound.memory;

import com.proyectosu.invernadero.image.domain.model.GreenhouseImage;
import com.proyectosu.invernadero.image.domain.port.ImageStoragePort;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryImageStorageAdapter implements ImageStoragePort {

    private final ConcurrentHashMap<String, GreenhouseImage> latestImagesByDeviceId = new ConcurrentHashMap<>();

    @Override
    public void storeLatest(GreenhouseImage image) {
        latestImagesByDeviceId.put(image.getDeviceId(), image);
    }

    @Override
    public Optional<GreenhouseImage> findLatestByDeviceId(String deviceId) {
        return Optional.ofNullable(latestImagesByDeviceId.get(deviceId));
    }
}
