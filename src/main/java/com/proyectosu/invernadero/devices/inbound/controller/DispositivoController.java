package com.proyectosu.invernadero.devices.inbound.controller;

import com.proyectosu.invernadero.devices.application.usecase.ObtenerDispositivosUseCase;
import com.proyectosu.invernadero.devices.dto.response.DispositivoResponse;
import com.proyectosu.invernadero.devices.infrastructure.persistence.mapper.DispositivoResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/devices")
@RequiredArgsConstructor
public class DispositivoController {

    private final ObtenerDispositivosUseCase obtenerDispositivosUseCase;
    private final DispositivoResponseMapper responseMapper;

    @GetMapping
    public ResponseEntity<List<DispositivoResponse>> listarDispositivos(
            @RequestParam(name = "device_id", required = false) String deviceId
    ) {
        List<DispositivoResponse> devices = obtenerDispositivosUseCase.ejecutar(deviceId)
                .stream()
                .map(responseMapper::toResponse)
                .toList();

        return ResponseEntity.ok(devices);
    }
}