package com.proyectosu.invernadero.alerts.application.usecase;

import com.proyectosu.invernadero.alerts.domain.model.Alert;
import com.proyectosu.invernadero.alerts.domain.ports.AlertRepositoryPort;
import com.proyectosu.invernadero.shared.errores.ApiException;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeParseException;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class ListAlertsUseCase {

    private static final String INVALID_FILTER_CODE = "INVALID_ALERT_FILTER";

    private final AlertRepositoryPort alertRepositoryPort;

    public ListAlertsUseCase(AlertRepositoryPort alertRepositoryPort) {
        this.alertRepositoryPort = alertRepositoryPort;
    }

    public List<Alert> ejecutar(String greenhouseId, String from, String to) {
        Instant fromInstant = parseLowerBound(from);
        Instant toInstant = parseUpperBound(to);

        if (fromInstant != null && toInstant != null && fromInstant.isAfter(toInstant)) {
            throw invalidFilter("from must be lower than to");
        }

        String normalizedGreenhouseId = normalizeOptionalValue(greenhouseId);

        return alertRepositoryPort.findAll().stream()
                .filter(alert -> normalizedGreenhouseId == null || alert.getGreenhouseId().equalsIgnoreCase(normalizedGreenhouseId))
                .filter(alert -> fromInstant == null || !alert.getTimestamp().isBefore(fromInstant))
                .filter(alert -> toInstant == null || !alert.getTimestamp().isAfter(toInstant))
                .sorted(Comparator.comparing(Alert::getTimestamp).reversed())
                .collect(Collectors.toList());
    }

    private Instant parseLowerBound(String value) {
        return parseBound(value, false);
    }

    private Instant parseUpperBound(String value) {
        return parseBound(value, true);
    }

    private Instant parseBound(String value, boolean endOfDay) {
        String normalized = normalizeOptionalValue(value);
        if (normalized == null) {
            return null;
        }

        try {
            LocalDate date = LocalDate.parse(normalized);
            return endOfDay
                    ? date.plusDays(1).atStartOfDay(ZoneOffset.UTC).minusNanos(1).toInstant()
                    : date.atStartOfDay(ZoneOffset.UTC).toInstant();
        } catch (DateTimeParseException ignored) {
            try {
                return Instant.parse(normalized);
            } catch (Exception ex) {
                throw invalidFilter("Invalid alert filter");
            }
        }
    }

    private String normalizeOptionalValue(String value) {
        if (value == null) {
            return null;
        }

        String normalized = value.trim();
        return normalized.isBlank() ? null : normalized;
    }

    private ApiException invalidFilter(String message) {
        return new ApiException(HttpStatus.BAD_REQUEST, INVALID_FILTER_CODE, message);
    }
}