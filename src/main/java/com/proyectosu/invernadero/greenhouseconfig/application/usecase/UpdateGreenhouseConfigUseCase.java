package com.proyectosu.invernadero.greenhouseconfig.application.usecase;

import com.proyectosu.invernadero.greenhouseconfig.domain.model.GreenhouseConfig;
import com.proyectosu.invernadero.greenhouseconfig.domain.ports.GreenhouseConfigRepositoryPort;
import com.proyectosu.invernadero.greenhouseconfig.dto.request.GreenhouseConfigRequest;
import com.proyectosu.invernadero.shared.errores.ApiException;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.time.ZoneId;

public class UpdateGreenhouseConfigUseCase {

    private static final String GLOBAL_KEY = "global";
    private static final String INVALID_CONFIGURATION_CODE = "INVALID_CONFIGURATION";

    private final GreenhouseConfigRepositoryPort repositoryPort;

    public UpdateGreenhouseConfigUseCase(GreenhouseConfigRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    public GreenhouseConfig ejecutar(GreenhouseConfigRequest request) {
        GreenhouseConfig current = repositoryPort.read();

        Boolean automaticMode = request.getAutomatic_mode() != null
                ? request.getAutomatic_mode()
                : current.getAutomaticMode();

        Integer inactivityThresholdMinutes = request.getInactivity_threshold_minutes() != null
                ? request.getInactivity_threshold_minutes()
                : current.getInactivityThresholdMinutes();

        Integer manualOverrideDurationMinutes = request.getManual_override_duration_minutes() != null
                ? request.getManual_override_duration_minutes()
                : current.getManualOverrideDurationMinutes();

        String reportTimezone = request.getReport_timezone() != null
                ? normalizeTimezone(request.getReport_timezone())
                : current.getReportTimezone();

        validateThreshold(inactivityThresholdMinutes, "inactivity_threshold_minutes");
        validateThreshold(manualOverrideDurationMinutes, "manual_override_duration_minutes");

        GreenhouseConfig updated = new GreenhouseConfig(
                GLOBAL_KEY,
                automaticMode,
                inactivityThresholdMinutes,
                manualOverrideDurationMinutes,
                reportTimezone,
                Instant.now()
        );

        return repositoryPort.save(updated);
    }

    private void validateThreshold(Integer value, String fieldName) {
        if (value == null || value <= 0) {
            throw new ApiException(HttpStatus.BAD_REQUEST, INVALID_CONFIGURATION_CODE, fieldName + " debe ser mayor que cero");
        }
    }

    private String normalizeTimezone(String value) {
        String trimmed = value.trim();
        if (trimmed.isBlank()) {
            throw new ApiException(HttpStatus.BAD_REQUEST, INVALID_CONFIGURATION_CODE, "report_timezone es obligatoria");
        }

        try {
            ZoneId.of(trimmed);
        } catch (Exception ex) {
            throw new ApiException(HttpStatus.BAD_REQUEST, INVALID_CONFIGURATION_CODE, "report_timezone es invalida");
        }

        return trimmed;
    }
}