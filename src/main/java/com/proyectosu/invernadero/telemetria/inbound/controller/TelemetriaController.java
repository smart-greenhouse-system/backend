package com.proyectosu.invernadero.telemetria.inbound.controller;

import com.proyectosu.invernadero.telemetria.application.usecase.ObtenerUltimaMedicionUseCase;
import com.proyectosu.invernadero.telemetria.domain.model.MedicionTelemetria;
import com.proyectosu.invernadero.telemetria.dto.response.MedicionTelemetriaResponse;
import com.proyectosu.invernadero.telemetria.dto.response.UltimaMedicionResponse;
import com.proyectosu.invernadero.telemetria.infrastructure.persistence.mapper.MedicionTelemetriaResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/devices")
@RequiredArgsConstructor
public class TelemetriaController {

    private final ObtenerUltimaMedicionUseCase obtenerUltimaMedicionUseCase;
    private final MedicionTelemetriaResponseMapper responseMapper;

    @GetMapping("/{device_id}/measurements/latest")
    public ResponseEntity<UltimaMedicionResponse> obtenerUltimaMedicion(
            @PathVariable("device_id") String deviceId
    ) {
        String normalizedDeviceId = deviceId == null ? "" : deviceId.trim();

        if (normalizedDeviceId.isBlank()) {
            throw new IllegalArgumentException("El device_id es obligatorio");
        }

        Optional<MedicionTelemetria> ultimaMedicion = obtenerUltimaMedicionUseCase.ejecutar(normalizedDeviceId);
        MedicionTelemetriaResponse latest = ultimaMedicion.map(responseMapper::toResponse).orElse(null);

        return ResponseEntity.ok(new UltimaMedicionResponse(normalizedDeviceId, latest));
    }
}