package com.proyectosu.invernadero.mqtt.inbound;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyectosu.invernadero.mqtt.application.usecase.GuardarEstadoActuadorUseCase;
import com.proyectosu.invernadero.mqtt.application.usecase.GuardarImagenCamUseCase;
import com.proyectosu.invernadero.mqtt.application.usecase.GuardarSensorNodo1UseCase;
import com.proyectosu.invernadero.mqtt.application.usecase.GuardarSensorNodo2UseCase;
import com.proyectosu.invernadero.mqtt.inbound.dto.request.EstadoActuadorRequest;
import com.proyectosu.invernadero.mqtt.inbound.dto.request.ImagenCamRequest;
import com.proyectosu.invernadero.mqtt.inbound.dto.request.SensorNodo1Request;
import com.proyectosu.invernadero.mqtt.inbound.dto.request.SensorNodo2Request;
import com.proyectosu.invernadero.mqtt.inbound.mapper.EstadoActuadorInboundMapper;
import com.proyectosu.invernadero.mqtt.inbound.mapper.ImagenCamInboundMapper;
import com.proyectosu.invernadero.mqtt.inbound.mapper.SensorNodo1InboundMapper;
import com.proyectosu.invernadero.mqtt.inbound.mapper.SensorNodo2InboundMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class MqttListenerAdapter {

    private static final String TOPIC_NODO1_SENSORES = "invernadero/nodo1/sensores";
    private static final String TOPIC_NODO2_SENSORES = "invernadero/nodo2/sensores";
    private static final String TOPIC_CAM_IMAGEN = "invernadero/cam/imagen";
    private static final String TOPIC_RIEGO_ESTADO = "invernadero/nodo1/actuadores/riego/estado";
    private static final String TOPIC_HUMIDIFICACION_ESTADO = "invernadero/nodo1/actuadores/humidificacion/estado";
    private static final String TOPIC_VENTILACION_ESTADO = "invernadero/nodo2/actuadores/ventilacion/estado";
    private static final String TOPIC_ILUMINACION_ESTADO = "invernadero/nodo2/actuadores/iluminacion/estado";

    private final ObjectMapper objectMapper;
    private final MqttPahoMessageDrivenChannelAdapter inbound;
    private final GuardarSensorNodo1UseCase guardarSensorNodo1UseCase;
    private final GuardarSensorNodo2UseCase guardarSensorNodo2UseCase;
    private final GuardarEstadoActuadorUseCase guardarEstadoActuadorUseCase;
    private final GuardarImagenCamUseCase guardarImagenCamUseCase;
    private final SensorNodo1InboundMapper sensorNodo1InboundMapper;
    private final SensorNodo2InboundMapper sensorNodo2InboundMapper;
    private final EstadoActuadorInboundMapper estadoActuadorInboundMapper;
    private final ImagenCamInboundMapper imagenCamInboundMapper;

    @PostConstruct
    public void suscribirTopics() {
        inbound.addTopic(TOPIC_NODO1_SENSORES, 0);
        inbound.addTopic(TOPIC_NODO2_SENSORES, 0);
        inbound.addTopic(TOPIC_CAM_IMAGEN, 1);
        inbound.addTopic(TOPIC_RIEGO_ESTADO, 1);
        inbound.addTopic(TOPIC_HUMIDIFICACION_ESTADO, 1);
        inbound.addTopic(TOPIC_VENTILACION_ESTADO, 1);
        inbound.addTopic(TOPIC_ILUMINACION_ESTADO, 1);
    }

    @ServiceActivator(inputChannel = "mqttInputChannel")
    public void handleMessage(@Header("mqtt_receivedTopic") String topic, String payload) {
        try {
            if (topic.contains("nodo1/sensores")) {
                SensorNodo1Request request = objectMapper.readValue(payload, SensorNodo1Request.class);
                handleNodo1(request);
                return;
            }

            if (topic.contains("nodo2/sensores")) {
                SensorNodo2Request request = objectMapper.readValue(payload, SensorNodo2Request.class);
                handleNodo2(request);
                return;
            }

            if (topic.contains("cam/imagen")) {
                ImagenCamRequest request = objectMapper.readValue(payload, ImagenCamRequest.class);
                handleImagen(request);
                return;
            }

            if (topic.contains("/estado")) {
                EstadoActuadorRequest request = objectMapper.readValue(payload, EstadoActuadorRequest.class);
                handleEstadoActuador(request);
                return;
            }

            log.warn("Topic MQTT no manejado: {} con payload: {}", topic, payload);
        } catch (JsonProcessingException e) {
            log.error("Error deserializando mensaje MQTT. topic={}, payload={}", topic, payload, e);
        } catch (Exception e) {
            log.error("Error procesando mensaje MQTT. topic={}, payload={}", topic, payload, e);
        }
    }

    private void handleNodo1(SensorNodo1Request request) {
        try {
            log.info("Mensaje nodo1 sensores recibido: {}", request);
            guardarSensorNodo1UseCase.ejecutar(sensorNodo1InboundMapper.toDomain(request));
        } catch (Exception e) {
            log.error("Error procesando datos de nodo1 sensores. request={}", request, e);
        }
    }

    private void handleNodo2(SensorNodo2Request request) {
        try {
            log.info("Mensaje nodo2 sensores recibido: {}", request);
            guardarSensorNodo2UseCase.ejecutar(sensorNodo2InboundMapper.toDomain(request));
        } catch (Exception e) {
            log.error("Error procesando datos de nodo2 sensores. request={}", request, e);
        }
    }

    private void handleImagen(ImagenCamRequest request) {
        try {
            log.info("Imagen camara recibida: {}", request);
            guardarImagenCamUseCase.ejecutar(imagenCamInboundMapper.toDomain(request));
        } catch (Exception e) {
            log.error("Error procesando imagen de camara. request={}", request, e);
        }
    }

    private void handleEstadoActuador(EstadoActuadorRequest request) {
        try {
            log.info("Estado de actuador recibido: {}", request);
            guardarEstadoActuadorUseCase.ejecutar(estadoActuadorInboundMapper.toDomain(request));
        } catch (Exception e) {
            log.error("Error procesando estado de actuador. request={}", request, e);
        }
    }
}
