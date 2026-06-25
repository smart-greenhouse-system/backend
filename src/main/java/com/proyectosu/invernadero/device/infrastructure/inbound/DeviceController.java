package com.proyectosu.invernadero.device.infrastructure.inbound;

import com.proyectosu.invernadero.device.application.usecases.GetDevicesUseCase;
import com.proyectosu.invernadero.device.domain.model.Device;
import com.proyectosu.invernadero.device.dto.response.DeviceResponse;
import com.proyectosu.invernadero.device.infrastructure.mappers.DeviceDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/devices")
@RequiredArgsConstructor
public class DeviceController {

    private final GetDevicesUseCase getDevicesUseCase;
    private final DeviceDtoMapper deviceDtoMapper;

    @GetMapping
    public ResponseEntity<List<DeviceResponse>> getDevices() {
        List<Device> devices = getDevicesUseCase.execute();

        return ResponseEntity.ok(deviceDtoMapper.toResponseList(devices));
    }
}
