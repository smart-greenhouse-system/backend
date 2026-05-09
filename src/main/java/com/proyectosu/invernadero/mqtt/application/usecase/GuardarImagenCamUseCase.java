package com.proyectosu.invernadero.mqtt.application.usecase;

import com.proyectosu.invernadero.mqtt.domain.ImagenCam;
import com.proyectosu.invernadero.mqtt.domain.ports.ImagenCamRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GuardarImagenCamUseCase {

    private final ImagenCamRepositoryPort repositoryPort;

    public ImagenCam ejecutar(ImagenCam domain) {
        return repositoryPort.guardar(domain);
    }
}