package com.proyectosu.invernadero.actuator.infrastructure.inbound;

import com.proyectosu.invernadero.actuator.application.command.ExecuteActuatorCommand;
import com.proyectosu.invernadero.actuator.application.usecases.ExecuteActuatorUseCase;
import com.proyectosu.invernadero.actuator.dto.request.ExecuteActuatorRequest;
import com.proyectosu.invernadero.actuator.dto.response.ExecuteActuatorResponse;
import com.proyectosu.invernadero.actuator.infrastructure.mappers.ActuatorDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/actuators")
@RequiredArgsConstructor
public class ActuatorController {

    private final ExecuteActuatorUseCase executeActuatorUseCase;
    private final ActuatorDtoMapper actuatorDtoMapper;

    @PostMapping("/execute")
    public ResponseEntity<ExecuteActuatorResponse> executeActuator(
            @RequestBody ExecuteActuatorRequest request
    ) {
        ExecuteActuatorCommand command = actuatorDtoMapper.toCommand(request);

        executeActuatorUseCase.execute(command);

        return ResponseEntity.ok(actuatorDtoMapper.toResponse(command));
    }
}
