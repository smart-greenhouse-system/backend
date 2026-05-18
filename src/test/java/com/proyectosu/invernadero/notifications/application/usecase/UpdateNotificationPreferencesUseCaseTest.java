package com.proyectosu.invernadero.notifications.application.usecase;

import com.proyectosu.invernadero.auth.domain.model.User;
import com.proyectosu.invernadero.auth.domain.ports.UserRepositoryPort;
import com.proyectosu.invernadero.notifications.domain.model.NotificationPreferences;
import com.proyectosu.invernadero.notifications.domain.ports.NotificationPreferencesRepositoryPort;
import com.proyectosu.invernadero.notifications.dto.request.NotificationPreferencesRequest;
import com.proyectosu.invernadero.notifications.dto.request.NotificationPreferencesRequest.DoNotDisturbRequest;
import com.proyectosu.invernadero.notifications.dto.request.NotificationPreferencesRequest.NotificationChannelsRequest;
import com.proyectosu.invernadero.notifications.dto.request.NotificationPreferencesRequest.NotificationEventsRequest;
import com.proyectosu.invernadero.shared.errores.ApiException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateNotificationPreferencesUseCaseTest {

    @Mock
    private UserRepositoryPort userRepositoryPort;

    @Mock
    private NotificationPreferencesRepositoryPort notificationPreferencesRepositoryPort;

    private UpdateNotificationPreferencesUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new UpdateNotificationPreferencesUseCase(
                userRepositoryPort,
                notificationPreferencesRepositoryPort
        );
    }

    @Test
    void shouldSavePreferencesWhenConfigurationIsValid() {
        UUID userId = UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6");
        when(userRepositoryPort.findById(userId)).thenReturn(Optional.of(mock(User.class)));
        when(notificationPreferencesRepositoryPort.save(any(NotificationPreferences.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        NotificationPreferencesRequest request = buildRequest(
                true,
                false,
                true,
                false,
                true,
                false,
                true,
                true,
                "22:00",
                "07:00"
        );

        NotificationPreferences result = useCase.ejecutar(userId.toString(), request);

        assertEquals(userId.toString(), result.getUserId());
        assertTrue(result.isCriticalAlerts());
        assertFalse(result.isWarnings());
        assertTrue(result.isOfflineSensors());
        assertFalse(result.isActuatorFailures());
        assertTrue(result.isPushEnabled());
        assertFalse(result.isEmailEnabled());
        assertTrue(result.isInAppEnabled());
        assertTrue(result.isDoNotDisturbEnabled());
        assertEquals("22:00", result.getDoNotDisturbStartTime());
        assertEquals("07:00", result.getDoNotDisturbEndTime());

        verify(notificationPreferencesRepositoryPort).save(any(NotificationPreferences.class));
    }

    @Test
    void shouldRejectCriticalAlertsWithoutAnyChannel() {
        UUID userId = UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6");
        when(userRepositoryPort.findById(userId)).thenReturn(Optional.of(mock(User.class)));

        NotificationPreferencesRequest request = buildRequest(
                true,
                true,
                true,
                true,
                false,
                false,
                false,
                false,
                null,
                null
        );

        ApiException exception = assertThrows(ApiException.class, () -> useCase.ejecutar(userId.toString(), request));

        assertEquals("INVALID_NOTIFICATION_CONFIGURATION", exception.getCode());
    }

    private NotificationPreferencesRequest buildRequest(
            boolean criticalAlerts,
            boolean warnings,
            boolean offlineSensors,
            boolean actuatorFailures,
            boolean push,
            boolean email,
            boolean inApp,
            boolean doNotDisturbEnabled,
            String startTime,
            String endTime
    ) {
        NotificationEventsRequest events = new NotificationEventsRequest();
        events.setCritical_alerts(criticalAlerts);
        events.setWarnings(warnings);
        events.setOffline_sensors(offlineSensors);
        events.setActuator_failures(actuatorFailures);

        NotificationChannelsRequest channels = new NotificationChannelsRequest();
        channels.setPush(push);
        channels.setEmail(email);
        channels.setIn_app(inApp);

        DoNotDisturbRequest doNotDisturb = new DoNotDisturbRequest();
        doNotDisturb.setEnabled(doNotDisturbEnabled);
        doNotDisturb.setStart_time(startTime);
        doNotDisturb.setEnd_time(endTime);

        NotificationPreferencesRequest request = new NotificationPreferencesRequest();
        request.setEvents(events);
        request.setChannels(channels);
        request.setDo_not_disturb(doNotDisturb);
        return request;
    }
}