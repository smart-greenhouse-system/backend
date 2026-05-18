package com.proyectosu.invernadero.notifications.application.usecase;

import com.proyectosu.invernadero.auth.domain.ports.UserRepositoryPort;
import com.proyectosu.invernadero.notifications.domain.model.NotificationPreferences;
import com.proyectosu.invernadero.notifications.domain.ports.NotificationPreferencesRepositoryPort;
import com.proyectosu.invernadero.notifications.dto.request.NotificationPreferencesRequest;
import com.proyectosu.invernadero.shared.errores.ApiException;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class UpdateNotificationPreferencesUseCase {

    private static final String INVALID_CONFIGURATION_CODE = "INVALID_NOTIFICATION_CONFIGURATION";
    private static final String INVALID_CONFIGURATION_MESSAGE = "Invalid notification configuration";
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    private final UserRepositoryPort userRepositoryPort;
    private final NotificationPreferencesRepositoryPort notificationPreferencesRepositoryPort;

    public UpdateNotificationPreferencesUseCase(
            UserRepositoryPort userRepositoryPort,
            NotificationPreferencesRepositoryPort notificationPreferencesRepositoryPort
    ) {
        this.userRepositoryPort = userRepositoryPort;
        this.notificationPreferencesRepositoryPort = notificationPreferencesRepositoryPort;
    }

    public NotificationPreferences ejecutar(String userId, NotificationPreferencesRequest request) {
        String normalizedUserId = normalizeRequiredValue(userId, "El user_id es obligatorio");
        UUID parsedUserId = parseUserId(normalizedUserId);

        userRepositoryPort.findById(parsedUserId)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "USER_NOT_FOUND", "User not found"));

        validateRequest(request);

        NotificationPreferencesRequest.NotificationEventsRequest events = request.getEvents();
        NotificationPreferencesRequest.NotificationChannelsRequest channels = request.getChannels();
        NotificationPreferencesRequest.DoNotDisturbRequest doNotDisturb = request.getDo_not_disturb();

        boolean criticalAlerts = requireBoolean(events.getCritical_alerts(), "critical_alerts");
        boolean warnings = requireBoolean(events.getWarnings(), "warnings");
        boolean offlineSensors = requireBoolean(events.getOffline_sensors(), "offline_sensors");
        boolean actuatorFailures = requireBoolean(events.getActuator_failures(), "actuator_failures");

        boolean pushEnabled = requireBoolean(channels.getPush(), "push");
        boolean emailEnabled = requireBoolean(channels.getEmail(), "email");
        boolean inAppEnabled = requireBoolean(channels.getIn_app(), "in_app");

        boolean doNotDisturbEnabled = requireBoolean(doNotDisturb.getEnabled(), "enabled");
        String startTime = normalizeOptionalValue(doNotDisturb.getStart_time());
        String endTime = normalizeOptionalValue(doNotDisturb.getEnd_time());

        if (doNotDisturbEnabled) {
            if (startTime == null || endTime == null) {
                throw invalidConfiguration("do_not_disturb requiere start_time y end_time cuando esta habilitado");
            }

            validateTime(startTime, "start_time");
            validateTime(endTime, "end_time");
        } else {
            if (startTime != null) {
                validateTime(startTime, "start_time");
            }

            if (endTime != null) {
                validateTime(endTime, "end_time");
            }
        }

        NotificationPreferences preferences = new NotificationPreferences(
                normalizedUserId,
                criticalAlerts,
                warnings,
                offlineSensors,
                actuatorFailures,
                pushEnabled,
                emailEnabled,
                inAppEnabled,
                doNotDisturbEnabled,
                startTime,
                endTime,
                Instant.now()
        );

        return notificationPreferencesRepositoryPort.save(preferences);
    }

    private void validateRequest(NotificationPreferencesRequest request) {
        if (request == null || request.getEvents() == null || request.getChannels() == null || request.getDo_not_disturb() == null) {
            throw invalidConfiguration();
        }

        if (Boolean.TRUE.equals(request.getEvents().getCritical_alerts()) && !hasAtLeastOneChannel(request.getChannels())) {
            throw invalidConfiguration("At least one channel must be enabled when critical alerts are active");
        }
    }

    private boolean hasAtLeastOneChannel(NotificationPreferencesRequest.NotificationChannelsRequest channels) {
        return Boolean.TRUE.equals(channels.getPush())
                || Boolean.TRUE.equals(channels.getEmail())
                || Boolean.TRUE.equals(channels.getIn_app());
    }

    private void validateTime(String value, String fieldName) {
        try {
            LocalTime.parse(value, TIME_FORMATTER);
        } catch (Exception ex) {
            throw invalidConfiguration(fieldName + " debe tener formato HH:mm");
        }
    }

    private boolean requireBoolean(Boolean value, String fieldName) {
        if (value == null) {
            throw invalidConfiguration(fieldName + " es obligatorio");
        }

        return value;
    }

    private ApiException invalidConfiguration() {
        return new ApiException(HttpStatus.BAD_REQUEST, INVALID_CONFIGURATION_CODE, INVALID_CONFIGURATION_MESSAGE);
    }

    private ApiException invalidConfiguration(String message) {
        return new ApiException(HttpStatus.BAD_REQUEST, INVALID_CONFIGURATION_CODE, message);
    }

    private UUID parseUserId(String userId) {
        try {
            return UUID.fromString(userId);
        } catch (IllegalArgumentException ex) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "INVALID_USER_ID", "Invalid user_id");
        }
    }

    private String normalizeRequiredValue(String value, String errorMessage) {
        String normalizedValue = value == null ? "" : value.trim();

        if (normalizedValue.isBlank()) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "INVALID_USER_ID", errorMessage);
        }

        return normalizedValue;
    }

    private String normalizeOptionalValue(String value) {
        if (value == null) {
            return null;
        }

        String normalizedValue = value.trim();
        return normalizedValue.isBlank() ? null : normalizedValue;
    }
}