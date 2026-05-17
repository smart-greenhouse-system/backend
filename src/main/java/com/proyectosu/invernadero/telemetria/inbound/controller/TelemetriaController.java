package com.proyectosu.invernadero.telemetria.inbound.controller;

import com.proyectosu.invernadero.telemetria.application.usecase.ObtenerListadoMedicionesUseCase;
import com.proyectosu.invernadero.telemetria.application.usecase.ObtenerHistoricoMedicionesUseCase;
import com.proyectosu.invernadero.telemetria.application.usecase.ObtenerUltimaMedicionUseCase;
import com.proyectosu.invernadero.telemetria.domain.model.MedicionTelemetria;
import com.proyectosu.invernadero.telemetria.dto.response.HistoricoMedicionesResponse;
import com.proyectosu.invernadero.telemetria.dto.response.MedicionTelemetriaResponse;
import com.proyectosu.invernadero.telemetria.dto.response.UltimaMedicionResponse;
import com.proyectosu.invernadero.telemetria.infrastructure.persistence.mapper.MedicionTelemetriaResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sensors")
@RequiredArgsConstructor
public class TelemetriaController {

    private static final int MAX_LIMIT = 1000;

    private final ObtenerUltimaMedicionUseCase obtenerUltimaMedicionUseCase;
    private final ObtenerHistoricoMedicionesUseCase obtenerHistoricoMedicionesUseCase;
    private final ObtenerListadoMedicionesUseCase obtenerListadoMedicionesUseCase;
    private final MedicionTelemetriaResponseMapper responseMapper;

    @GetMapping("/latest")
    public ResponseEntity<UltimaMedicionResponse> obtenerUltimaMedicion(
            @RequestParam(name = "device_id", required = false) String deviceId
    ) {
        return construirRespuestaUltimaMedicion(normalizeDeviceId(deviceId));
    }

    @GetMapping("/{device_id}/measurements/latest")
    public ResponseEntity<UltimaMedicionResponse> obtenerUltimaMedicionLegado(
            @PathVariable("device_id") String deviceId
    ) {
        return construirRespuestaUltimaMedicion(normalizeDeviceId(deviceId));
    }

    @GetMapping("/history/{device_id}")
    public ResponseEntity<HistoricoMedicionesResponse> obtenerHistorico(
            @PathVariable("device_id") String deviceId,
            @RequestParam(name = "from", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant from,
            @RequestParam(name = "to", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant to,
            @RequestParam(name = "limit", required = false, defaultValue = "100") int limit
    ) {
        return construirRespuestaHistorico(normalizeDeviceId(deviceId), from, to, limit);
    }

    @GetMapping("/{device_id}/measurements")
    public ResponseEntity<HistoricoMedicionesResponse> obtenerHistoricoLegado(
            @PathVariable("device_id") String deviceId,
            @RequestParam(name = "from", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant from,
            @RequestParam(name = "to", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant to,
            @RequestParam(name = "limit", required = false, defaultValue = "100") int limit
    ) {
        return construirRespuestaHistorico(normalizeDeviceId(deviceId), from, to, limit);
    }

    @GetMapping("/{device_id}/measurements/list")
    public ResponseEntity<HistoricoMedicionesResponse> listarMedicionesPorDispositivo(
            @PathVariable("device_id") String deviceId,
            @RequestParam(name = "limit", required = false, defaultValue = "100") int limit
    ) {
        return construirRespuestaListado(normalizeDeviceId(deviceId), limit);
    }

    private ResponseEntity<UltimaMedicionResponse> construirRespuestaUltimaMedicion(String normalizedDeviceId) {
        Optional<MedicionTelemetria> ultimaMedicion = obtenerUltimaMedicionUseCase.ejecutar(normalizedDeviceId);
        MedicionTelemetriaResponse latest = ultimaMedicion.map(responseMapper::toResponse).orElse(null);

        return ResponseEntity.ok(new UltimaMedicionResponse(normalizedDeviceId, latest));
    }

    private ResponseEntity<HistoricoMedicionesResponse> construirRespuestaHistorico(
            String normalizedDeviceId,
            Instant from,
            Instant to,
            int limit
    ) {
        if (from != null && to != null && from.isAfter(to)) {
            throw new IllegalArgumentException("El rango de fechas no es valido: from no puede ser mayor que to");
        }

        if (limit < 1) {
            throw new IllegalArgumentException("El limit debe ser mayor que cero");
        }

        if (limit > MAX_LIMIT) {
            throw new IllegalArgumentException("El limit supera el maximo permitido");
        }

        List<MedicionTelemetriaResponse> readings = obtenerHistoricoMedicionesUseCase
                .ejecutar(normalizedDeviceId, from, to, limit)
                .stream()
                .map(responseMapper::toResponse)
                .toList();

        return ResponseEntity.ok(new HistoricoMedicionesResponse(normalizedDeviceId, readings.size(), readings));
    }

    private ResponseEntity<HistoricoMedicionesResponse> construirRespuestaListado(String normalizedDeviceId, int limit) {
        if (limit < 1) {
            throw new IllegalArgumentException("El limit debe ser mayor que cero");
        }

        if (limit > MAX_LIMIT) {
            throw new IllegalArgumentException("El limit supera el maximo permitido");
        }

        List<MedicionTelemetriaResponse> readings = obtenerListadoMedicionesUseCase
                .ejecutar(normalizedDeviceId, limit)
                .stream()
                .map(responseMapper::toResponse)
                .toList();

        return ResponseEntity.ok(new HistoricoMedicionesResponse(normalizedDeviceId, readings.size(), readings));
    }

    private String normalizeDeviceId(String deviceId) {
        String normalizedDeviceId = deviceId == null ? "" : deviceId.trim();

        if (normalizedDeviceId.isBlank()) {
            throw new IllegalArgumentException("El device_id es obligatorio");
        }

        return normalizedDeviceId;
    }
}