package com.proyectosu.invernadero.actuators.inbound.controller;

import com.proyectosu.invernadero.actuators.application.usecase.ExecuteActuatorUseCase;
import com.proyectosu.invernadero.actuators.domain.model.ActuatorEvent;
import com.proyectosu.invernadero.actuators.dto.request.ActuatorCommandRequest;
import com.proyectosu.invernadero.actuators.dto.response.ActuatorCommandResponse;
import com.proyectosu.invernadero.actuators.infrastructure.persistence.mapper.ActuatorCommandResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/actuators")
@RequiredArgsConstructor
public class ActuadoresController {

    private final ExecuteActuatorUseCase executeActuatorUseCase;
    private final ActuatorCommandResponseMapper responseMapper;

    @PostMapping("/execute")
    public ResponseEntity<ActuatorCommandResponse> ejecutar(@RequestBody ActuatorCommandRequest request) {
        ActuatorEvent actuatorEvent = executeActuatorUseCase.ejecutar(
                request.getDevice_id(),
                request.getActuador(),
                request.getAccion()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(responseMapper.toResponse(actuatorEvent));
    }
}