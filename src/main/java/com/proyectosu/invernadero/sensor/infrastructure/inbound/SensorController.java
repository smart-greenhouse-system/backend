package com.proyectosu.invernadero.sensor.infrastructure.inbound;

import com.proyectosu.invernadero.sensor.application.usecases.GetLatestSensorDataUseCase;
import com.proyectosu.invernadero.sensor.application.usecases.GetSensorHistoryUseCase;
import com.proyectosu.invernadero.sensor.domain.model.SensorData;
import com.proyectosu.invernadero.sensor.dto.response.SensorDataResponse;
import com.proyectosu.invernadero.sensor.infrastructure.mappers.SensorDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/sensors")
@RequiredArgsConstructor
public class SensorController {

    private final GetLatestSensorDataUseCase getLatestSensorDataUseCase;
    private final GetSensorHistoryUseCase getSensorHistoryUseCase;
    private final SensorDtoMapper sensorDtoMapper;

    @GetMapping("/latest")
    public ResponseEntity<SensorDataResponse> getLatest() {
        SensorData sensorData = getLatestSensorDataUseCase.execute();

        return ResponseEntity.ok(sensorDtoMapper.toResponse(sensorData));
    }

    @GetMapping("/history/{device_id}")
    public ResponseEntity<List<SensorDataResponse>> getHistory(
            @PathVariable("device_id") String deviceId
    ) {
        List<SensorData> history = getSensorHistoryUseCase.execute(deviceId);

        return ResponseEntity.ok(sensorDtoMapper.toResponseList(history));
    }
}
