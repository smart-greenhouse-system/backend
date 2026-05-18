package com.proyectosu.invernadero.thresholds.application.usecase;

import com.proyectosu.invernadero.shared.errores.ApiException;
import com.proyectosu.invernadero.thresholds.domain.model.ThresholdRule;
import com.proyectosu.invernadero.thresholds.domain.ports.ThresholdRuleRepositoryPort;
import com.proyectosu.invernadero.thresholds.dto.request.ThresholdRuleRequest;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

public class SaveThresholdRuleUseCase {

    private static final String INVALID_CONFIGURATION_CODE = "INVALID_THRESHOLD_CONFIGURATION";
    private static final String INVALID_RANGE_CODE = "INVALID_THRESHOLD_RANGE";
    private static final String SUCCESS_MESSAGE = "Threshold rule created successfully";

    private static final List<String> VARIABLES_VALIDAS = List.of(
            "temperature",
            "air_humidity",
            "soil_moisture",
            "light_intensity"
    );

    private static final List<String> SEVERITIES_VALIDAS = List.of("warning", "critical");

    private static final Map<String, String> VARIABLE_UNITOS = Map.of(
            "temperature", "C",
            "air_humidity", "%",
            "soil_moisture", "%",
            "light_intensity", "lux"
    );

    private final ThresholdRuleRepositoryPort thresholdRuleRepositoryPort;

    public SaveThresholdRuleUseCase(ThresholdRuleRepositoryPort thresholdRuleRepositoryPort) {
        this.thresholdRuleRepositoryPort = thresholdRuleRepositoryPort;
    }

    public ThresholdRule ejecutar(ThresholdRuleRequest request) {
        if (request == null) {
            throw invalidConfiguration("Threshold rule request is required");
        }

        String greenhouseId = normalizeRequiredValue(request.getGreenhouse_id(), "greenhouse_id es obligatorio");
        String variable = normalizeVariable(request.getVariable());
        BigDecimal minValue = request.getMin_value();
        BigDecimal maxValue = request.getMax_value();
        String unit = normalizeUnit(request.getUnit());
        String severity = normalizeSeverity(request.getSeverity());
        boolean enabled = Boolean.TRUE.equals(request.getEnabled());

        if (minValue == null || maxValue == null) {
            throw invalidConfiguration("min_value y max_value son obligatorios");
        }

        if (minValue.compareTo(maxValue) >= 0) {
            throw new ApiException(HttpStatus.BAD_REQUEST, INVALID_RANGE_CODE, "Minimum value must be lower than maximum value");
        }

        validateUnitMatchesVariable(variable, unit);

        Instant now = Instant.now();
        ThresholdRule existingRule = thresholdRuleRepositoryPort.findByGreenhouseIdAndVariable(greenhouseId, variable)
                .orElse(null);

        ThresholdRule thresholdRule = new ThresholdRule(
                existingRule != null ? existingRule.getId() : UUID.randomUUID().toString(),
                greenhouseId,
                variable,
                minValue,
                maxValue,
                unit,
                severity,
                enabled,
                existingRule != null ? existingRule.getCreatedAt() : now,
                now
        );

        return thresholdRuleRepositoryPort.save(thresholdRule);
    }

    private void validateUnitMatchesVariable(String variable, String unit) {
        String expectedUnit = VARIABLE_UNITOS.get(variable);
        if (expectedUnit == null) {
            throw invalidConfiguration("variable no soportada");
        }

        if (!expectedUnit.equalsIgnoreCase(unit)) {
            throw invalidConfiguration("unit does not match selected variable");
        }
    }

    private String normalizeVariable(String value) {
        String normalized = normalizeRequiredValue(value, "variable es obligatoria").toLowerCase(Locale.ROOT);

        if (!VARIABLES_VALIDAS.contains(normalized)) {
            throw invalidConfiguration("variable no soportada");
        }

        return normalized;
    }

    private String normalizeSeverity(String value) {
        String normalized = normalizeRequiredValue(value, "severity es obligatoria").toLowerCase(Locale.ROOT);

        if (!SEVERITIES_VALIDAS.contains(normalized)) {
            throw invalidConfiguration("severity no soportada");
        }

        return normalized;
    }

    private String normalizeUnit(String value) {
        String normalized = normalizeRequiredValue(value, "unit es obligatoria");

        if (normalized.equalsIgnoreCase("C")) {
            return "C";
        }

        if (normalized.equals("%")) {
            return "%";
        }

        if (normalized.equalsIgnoreCase("lux")) {
            return "lux";
        }

        throw invalidConfiguration("unit no soportada");
    }

    private String normalizeRequiredValue(String value, String errorMessage) {
        String normalized = value == null ? "" : value.trim();
        if (normalized.isBlank()) {
            throw invalidConfiguration(errorMessage);
        }

        return normalized;
    }

    private ApiException invalidConfiguration(String message) {
        return new ApiException(HttpStatus.BAD_REQUEST, INVALID_CONFIGURATION_CODE, message);
    }
}