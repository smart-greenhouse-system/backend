package com.proyectosu.invernadero.sensor.infrastructure.inbound.mqtt;

import com.proyectosu.invernadero.sensor.application.command.SaveSensorDataCommand;
import com.proyectosu.invernadero.sensor.application.usecases.SaveSensorDataUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tools.jackson.databind.JsonNode;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
@Slf4j
public class SensorMessageHandler {

    private static final Pattern SENSOR_TOPIC_PATTERN =
            Pattern.compile("^invernadero/([^/]+)/sensores$");

    private final SaveSensorDataUseCase saveSensorDataUseCase;

    public boolean supports(String topic) {
        return SENSOR_TOPIC_PATTERN.matcher(topic).matches();
    }

    public void handle(String topic, JsonNode json) {
        Matcher matcher = SENSOR_TOPIC_PATTERN.matcher(topic);
        if (!matcher.matches()) {
            return;
        }

        String deviceIdFromTopic = matcher.group(1);
        String deviceId = json.hasNonNull("device_id")
                ? json.get("device_id").asText()
                : deviceIdFromTopic;

        JsonNode sensoresNode = json.get("sensores");
        if (sensoresNode == null || !sensoresNode.isObject()) {
            log.warn("Mensaje ignorado en el tópico [{}]. Falta el objeto sensores válido", topic);
            return;
        }

        Map<String, Double> sensores = parseSensores(sensoresNode);
        if (sensores.isEmpty()) {
            log.warn("Mensaje ignorado en el tópico [{}]. El objeto sensores está vacío", topic);
            return;
        }

        try {
            saveSensorDataUseCase.execute(
                    SaveSensorDataCommand.builder()
                            .deviceId(deviceId)
                            .sensores(sensores)
                            .build()
            );
        } catch (IllegalArgumentException exception) {
            log.warn("Mensaje ignorado en el tópico [{}]. {}", topic, exception.getMessage());
        }
    }

    private Map<String, Double> parseSensores(JsonNode sensoresNode) {
        Map<String, Double> sensores = new HashMap<>();
        Iterator<Map.Entry<String, JsonNode>> fields = sensoresNode.properties().iterator();

        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> field = fields.next();
            JsonNode valueNode = field.getValue();

            if (valueNode == null || !valueNode.isNumber()) {
                log.warn("Valor inválido para el sensor [{}], se omitirá", field.getKey());
                continue;
            }

            sensores.put(field.getKey(), valueNode.asDouble());
        }

        return sensores;
    }
}
