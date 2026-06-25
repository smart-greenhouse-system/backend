package com.proyectosu.invernadero.actuator.infrastructure.inbound;

import com.proyectosu.invernadero.actuator.application.command.ExecuteActuatorCommand;
import com.proyectosu.invernadero.actuator.application.usecases.CreateActuatorUseCase;
import com.proyectosu.invernadero.actuator.application.usecases.DeleteActuatorUseCase;
import com.proyectosu.invernadero.actuator.application.usecases.ExecuteActuatorUseCase;
import com.proyectosu.invernadero.actuator.application.usecases.GetActuatorsUseCase;
import com.proyectosu.invernadero.actuator.application.usecases.UpdateActuatorUseCase;
import com.proyectosu.invernadero.actuator.domain.model.Actuator;
import com.proyectosu.invernadero.actuator.dto.request.CreateActuatorRequest;
import com.proyectosu.invernadero.actuator.dto.request.ExecuteActuatorRequest;
import com.proyectosu.invernadero.actuator.dto.request.UpdateActuatorRequest;
import com.proyectosu.invernadero.actuator.dto.response.ActuatorResponse;
import com.proyectosu.invernadero.actuator.dto.response.ExecuteActuatorResponse;
import com.proyectosu.invernadero.actuator.infrastructure.mappers.ActuatorDtoMapper;
import com.proyectosu.invernadero.actuator.infrastructure.mappers.ActuatorManagementDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/actuators")
@RequiredArgsConstructor
public class ActuatorController {

    private final GetActuatorsUseCase getActuatorsUseCase;
    private final CreateActuatorUseCase createActuatorUseCase;
    private final UpdateActuatorUseCase updateActuatorUseCase;
    private final DeleteActuatorUseCase deleteActuatorUseCase;
    private final ExecuteActuatorUseCase executeActuatorUseCase;
    private final ActuatorManagementDtoMapper actuatorManagementDtoMapper;
    private final ActuatorDtoMapper actuatorDtoMapper;

    @GetMapping
    public ResponseEntity<List<ActuatorResponse>> getActuators() {
        List<Actuator> actuators = getActuatorsUseCase.execute();

        return ResponseEntity.ok(actuatorManagementDtoMapper.toResponseList(actuators));
    }

    @PostMapping
    public ResponseEntity<ActuatorResponse> createActuator(
            @RequestBody CreateActuatorRequest request
    ) {
        Actuator createdActuator = createActuatorUseCase.execute(
                actuatorManagementDtoMapper.toCreateCommand(request)
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(actuatorManagementDtoMapper.toResponse(createdActuator));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ActuatorResponse> updateActuator(
            @PathVariable String id,
            @RequestBody UpdateActuatorRequest request
    ) {
        Actuator updatedActuator = updateActuatorUseCase.execute(
                actuatorManagementDtoMapper.toUpdateCommand(id, request)
        );

        return ResponseEntity.ok(actuatorManagementDtoMapper.toResponse(updatedActuator));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActuator(@PathVariable String id) {
        deleteActuatorUseCase.execute(id);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/execute")
    public ResponseEntity<ExecuteActuatorResponse> executeActuator(
            @RequestBody ExecuteActuatorRequest request
    ) {
        ExecuteActuatorCommand command = actuatorDtoMapper.toCommand(request);

        executeActuatorUseCase.execute(command);

        return ResponseEntity.ok(actuatorDtoMapper.toResponse(command));
    }
}
