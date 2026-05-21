package com.proyectosu.invernadero.image.infrastructure.inbound.mqtt;

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

        try {
            storeLatestImageUseCase.execute(
                    StoreImageCommand.builder()
                            .deviceId(json.get("device_id").asText())
                            .format(json.get("formato").asText())
                            .resolution(json.get("resolucion").asText())
                            .imageBase64(json.get("imagen").asText())
                            .build()
            );
        } catch (IllegalArgumentException exception) {
            log.warn("Mensaje ignorado en el tópico [{}]. {}", topic, exception.getMessage());
        }
    }
}
