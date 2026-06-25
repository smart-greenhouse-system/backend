package com.proyectosu.invernadero.actuator.infrastructure.inbound;

import com.proyectosu.invernadero.actuator.application.usecases.GetActuatorEventsUseCase;
import com.proyectosu.invernadero.actuator.domain.model.ActuatorEvent;
import com.proyectosu.invernadero.actuator.dto.response.ActuatorEventResponse;
import com.proyectosu.invernadero.actuator.infrastructure.mappers.ActuatorEventDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/actuator-events")
@RequiredArgsConstructor
public class ActuatorEventController {

    private final GetActuatorEventsUseCase getActuatorEventsUseCase;
    private final ActuatorEventDtoMapper actuatorEventDtoMapper;

    @GetMapping
    public ResponseEntity<List<ActuatorEventResponse>> getActuatorEvents() {
        List<ActuatorEvent> events = getActuatorEventsUseCase.execute();

        return ResponseEntity.ok(actuatorEventDtoMapper.toResponseList(events));
    }

    @GetMapping("/{deviceId}")
    public ResponseEntity<List<ActuatorEventResponse>> getActuatorEventsByDevice(
            @PathVariable String deviceId
    ) {
        List<ActuatorEvent> events = getActuatorEventsUseCase.executeByDeviceId(deviceId);

        return ResponseEntity.ok(actuatorEventDtoMapper.toResponseList(events));
    }
}
