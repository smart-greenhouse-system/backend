package com.proyectosu.invernadero.mqtt.inbound;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyectosu.invernadero.mqtt.inbound.dto.response.ComandoActuadorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@Slf4j
@RequiredArgsConstructor
public class MqttPublisherAdapter {

    private final MessageChannel mqttOutboundChannel;
    private final ObjectMapper objectMapper;

    public void publicar(String topic, Object payload, int qos) {
        try {
            String json = objectMapper.writeValueAsString(payload);

            mqttOutboundChannel.send(
                    MessageBuilder.withPayload(json)
                            .setHeader(MqttHeaders.TOPIC, topic)
                            .setHeader(MqttHeaders.QOS, qos)
                            .build()
            );
        } catch (Exception e) {
            log.error("Error publicando mensaje MQTT. topic={}, qos={}, payload={}", topic, qos, payload, e);
        }
    }

    public void comandoRiego(String deviceId, String accion) {
        ComandoActuadorResponse payload = buildComandoPayload(deviceId, "riego", accion);
        publicar("invernadero/" + deviceId + "/actuadores/riego/cmd", payload, 1);
    }

    public void comandoHumidificacion(String deviceId, String accion) {
        ComandoActuadorResponse payload = buildComandoPayload(deviceId, "humidificacion", accion);
        publicar("invernadero/" + deviceId + "/actuadores/humidificacion/cmd", payload, 1);
    }

    public void comandoVentilacion(String deviceId, String accion) {
        ComandoActuadorResponse payload = buildComandoPayload(deviceId, "ventilacion", accion);
        publicar("invernadero/" + deviceId + "/actuadores/ventilacion/cmd", payload, 1);
    }

    public void comandoIluminacion(String deviceId, String accion) {
        ComandoActuadorResponse payload = buildComandoPayload(deviceId, "iluminacion", accion);
        publicar("invernadero/" + deviceId + "/actuadores/iluminacion/cmd", payload, 1);
    }

    private ComandoActuadorResponse buildComandoPayload(String deviceId, String actuador, String accion) {
        return new ComandoActuadorResponse(
                deviceId,
                actuador,
                accion,
                Instant.now().toString()
        );
    }
}
