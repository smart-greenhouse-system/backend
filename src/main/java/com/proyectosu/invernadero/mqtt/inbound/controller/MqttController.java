package com.proyectosu.invernadero.mqtt.inbound.controller;

import com.proyectosu.invernadero.mqtt.application.usecase.GuardarMqttUseCase;
import com.proyectosu.invernadero.mqtt.application.usecase.ObtenerMqttUseCase;
import com.proyectosu.invernadero.mqtt.domain.Mqtt;
import com.proyectosu.invernadero.mqtt.inbound.dto.request.MqttRequest;
import com.proyectosu.invernadero.mqtt.inbound.dto.response.MqttResponse;
import com.proyectosu.invernadero.mqtt.inbound.mapper.MqttResponseMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mqtt")
@RequiredArgsConstructor
public class MqttController {

    private final GuardarMqttUseCase guardarMqttUseCase;
    private final ObtenerMqttUseCase obtenerMqttUseCase;
    private final MqttResponseMapper mqttResponseMapper;

    @PostMapping
    public ResponseEntity<MqttResponse> guardar(
            @Valid @RequestBody MqttRequest request) {

        Mqtt mqtt = guardarMqttUseCase.ejecutar(
                request.getTopico(),
                request.getMensaje()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(mqttResponseMapper.toResponse(mqtt));
    }

    @GetMapping
    public ResponseEntity<List<MqttResponse>> obtenerTodos() {
        List<MqttResponse> response = mqttResponseMapper.toResponseList(obtenerMqttUseCase.ejecutar());
        return ResponseEntity.ok(response);
    }
}
