package com.proyectosu.invernadero.actuator.infrastructure.inbound.mqtt;

import com.proyectosu.invernadero.actuator.application.command.ProcessActuatorStateCommand;
import com.proyectosu.invernadero.actuator.application.usecases.ProcessActuatorStateUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tools.jackson.databind.JsonNode;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
@Slf4j
public class ActuatorStateMessageHandler {

    private static final Pattern ACTUATOR_STATE_TOPIC_PATTERN =
            Pattern.compile("^invernadero/([^/]+)/actuadores/([^/]+)/estado$");

    private final ProcessActuatorStateUseCase processActuatorStateUseCase;

    public boolean supports(String topic) {
        return ACTUATOR_STATE_TOPIC_PATTERN.matcher(topic).matches();
    }

    public void handle(String topic, JsonNode json) {
        Matcher matcher = ACTUATOR_STATE_TOPIC_PATTERN.matcher(topic);
        if (!matcher.matches()) {
            return;
        }

        String deviceIdFromTopic = matcher.group(1);
        String actuadorFromTopic = matcher.group(2);

        if (!json.hasNonNull("device_id")) {
            log.warn("Mensaje ignorado en el tópico [{}]. Falta device_id", topic);
            return;
        }

        if (!json.hasNonNull("actuador")) {
            log.warn("Mensaje ignorado en el tópico [{}]. Falta actuador", topic);
            return;
        }

        if (!json.hasNonNull("estado")) {
            log.warn("Mensaje ignorado en el tópico [{}]. Falta estado", topic);
            return;
        }

        if (!json.has("ejecutado")) {
            log.warn("Mensaje ignorado en el tópico [{}]. Falta ejecutado", topic);
            return;
        }

        String deviceId = json.get("device_id").asText();
        String actuador = json.get("actuador").asText();

        if (!deviceId.equals(deviceIdFromTopic) || !actuador.equals(actuadorFromTopic)) {
            log.warn(
                    "Mensaje ignorado en el tópico [{}]. device_id/actuador no coinciden con el tópico",
                    topic
            );
            return;
        }

        try {
            processActuatorStateUseCase.execute(
                    ProcessActuatorStateCommand.builder()
                            .deviceId(deviceId)
                            .actuador(actuador)
                            .estado(json.get("estado").asText())
                            .executed(json.get("ejecutado").asBoolean())
                            .topic(topic)
                            .build()
            );

            log.info(
                    "Confirmación de actuador procesada en [{}] para dispositivo [{}] actuador [{}]",
                    topic,
                    deviceId,
                    actuador
            );
        } catch (IllegalArgumentException exception) {
            log.warn("Mensaje ignorado en el tópico [{}]. {}", topic, exception.getMessage());
        }
    }
}
