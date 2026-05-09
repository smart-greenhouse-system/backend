package com.proyectosu.invernadero.mqtt.infrastructure.persistence.mapper;

import com.proyectosu.invernadero.mqtt.domain.ImagenCam;
import com.proyectosu.invernadero.mqtt.infrastructure.persistence.document.ImagenCamDocument;
import org.springframework.stereotype.Component;

@Component
public class ImagenCamMapper {

    public ImagenCam toDomain(ImagenCamDocument document) {
        return new ImagenCam(
                document.getDeviceId(),
                document.getImagenBase64(),
                document.getTimestamp()
        );
    }

    public ImagenCamDocument toDocument(ImagenCam domain) {
        return new ImagenCamDocument(
                null,
                domain.getDeviceId(),
                domain.getImagenBase64(),
                domain.getTimestamp()
        );
    }
}
