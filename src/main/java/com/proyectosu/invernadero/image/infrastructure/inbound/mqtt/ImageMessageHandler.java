package com.proyectosu.invernadero.image.infrastructure.inbound.mqtt;

import com.proyectosu.invernadero.ai.application.command.AnalyzeImageCommand;
import com.proyectosu.invernadero.ai.application.usecases.AnalyzeImageUseCase;
import com.proyectosu.invernadero.image.application.command.StoreImageCommand;
import com.proyectosu.invernadero.image.application.usecases.StoreLatestImageUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tools.jackson.databind.JsonNode;

@Component
@RequiredArgsConstructor
@Slf4j
public class ImageMessageHandler {

    private static final String IMAGE_TOPIC = "invernadero/cam/imagen";

    private final AnalyzeImageUseCase analyzeImageUseCase;
    private final StoreLatestImageUseCase storeLatestImageUseCase;

    public boolean supports(String topic) {
        return IMAGE_TOPIC.equals(topic);
    }

    public void handle(String topic, JsonNode json) {
        if (!supports(topic)) {
            return;
        }

        if (!json.hasNonNull("device_id")) {
            log.warn("Mensaje ignorado en el tópico [{}]. Falta device_id", topic);
            return;
        }

        if (!json.hasNonNull("formato")) {
            log.warn("Mensaje ignorado en el tópico [{}]. Falta formato", topic);
            return;
        }

        if (!json.hasNonNull("resolucion")) {
            log.warn("Mensaje ignorado en el tópico [{}]. Falta resolucion", topic);
            return;
        }

        if (!json.hasNonNull("imagen")) {
            log.warn("Mensaje ignorado en el tópico [{}]. Falta imagen", topic);
            return;
        }

        String deviceId = json.get("device_id").asText();
        String imageBase64 = json.get("imagen").asText();

        try {
            analyzeImageUseCase.execute(
                    AnalyzeImageCommand.builder()
                            .deviceId(deviceId)
                            .imageBase64(imageBase64)
                            .build()
            );
        } catch (IllegalArgumentException | IllegalStateException exception) {
            log.warn("No se pudo analizar la imagen en el tópico [{}]. {}", topic, exception.getMessage());
        }

        try {
            storeLatestImageUseCase.execute(
                    StoreImageCommand.builder()
                            .deviceId(deviceId)
                            .format(json.get("formato").asText())
                            .resolution(json.get("resolucion").asText())
                            .imageBase64(imageBase64)
                            .build()
            );
        } catch (IllegalArgumentException exception) {
            log.warn("No se pudo almacenar la imagen en memoria en el tópico [{}]. {}", topic, exception.getMessage());
        }
    }
}
