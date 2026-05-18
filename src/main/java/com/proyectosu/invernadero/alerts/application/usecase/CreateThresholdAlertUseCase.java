package com.proyectosu.invernadero.alerts.application.usecase;

import com.proyectosu.invernadero.alerts.domain.model.Alert;
import com.proyectosu.invernadero.alerts.domain.ports.AlertNotificationPort;
import com.proyectosu.invernadero.alerts.domain.ports.AlertRepositoryPort;
import com.proyectosu.invernadero.alerts.dto.request.ThresholdAlertRequest;
import com.proyectosu.invernadero.shared.errores.ApiException;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class CreateThresholdAlertUseCase {

    private static final String INVALID_CONFIGURATION_CODE = "INVALID_ALERT_CONFIGURATION";
    private static final String NOTIFICATION_FAILURE_CODE = "NOTIFICATION_SERVICE_ERROR";
    private static final List<String> VARIABLES_VALIDAS = List.of(
            "temperature",
            "air_humidity",
            "soil_moisture",
            "light_intensity"
    );
    private static final List<String> SEVERITIES_VALIDAS = List.of("warning", "critical");

    private final AlertRepositoryPort alertRepositoryPort;
    private final AlertNotificationPort alertNotificationPort;

    public CreateThresholdAlertUseCase(
            AlertRepositoryPort alertRepositoryPort,
            AlertNotificationPort alertNotificationPort
    ) {
        this.alertRepositoryPort = alertRepositoryPort;
        this.alertNotificationPort = alertNotificationPort;
    }

    public Alert ejecutar(ThresholdAlertRequest request) {
        if (request == null) {
            throw invalidConfiguration("alert request is required");
        }

        String greenhouseId = normalizeRequiredValue(request.getGreenhouse_id(), "greenhouse_id es obligatorio");
        String sensorReadingId = normalizeRequiredValue(request.getSensor_reading_id(), "sensor_reading_id es obligatorio");
        String thresholdRuleId = normalizeRequiredValue(request.getThreshold_rule_id(), "threshold_rule_id es obligatorio");
        String source = normalizeVariable(request.getVariable());
        BigDecimal value = request.getValue();
        String severity = normalizeSeverity(request.getSeverity());

        if (value == null) {
            throw invalidConfiguration("value es obligatorio");
        }

        Alert alert = new Alert(
                UUID.randomUUID().toString(),
                greenhouseId,
                sensorReadingId,
                thresholdRuleId,
                source,
                buildDescription(source, value),
                severity,
                value,
                "pending",
                Instant.now()
        );

        Alert storedAlert = alertRepositoryPort.save(alert);

        try {
            alertNotificationPort.send(storedAlert);
            Alert sentAlert = storedAlert.withNotificationStatus("sent");
            return alertRepositoryPort.save(sentAlert);
        } catch (Exception ex) {
            Alert failedAlert = storedAlert.withNotificationStatus("failed");
            alertRepositoryPort.save(failedAlert);
            throw new ApiException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    NOTIFICATION_FAILURE_CODE,
                    "Alert was stored but notification could not be delivered"
            );
        }
    }

    private String buildDescription(String variable, BigDecimal value) {
        return "Threshold exceeded for " + variable + ": " + value.stripTrailingZeros().toPlainString();
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