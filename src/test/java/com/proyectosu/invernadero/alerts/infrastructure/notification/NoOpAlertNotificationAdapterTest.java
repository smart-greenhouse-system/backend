package com.proyectosu.invernadero.alerts.infrastructure.notification;

import com.proyectosu.invernadero.alerts.domain.model.Alert;
import com.proyectosu.invernadero.notifications.center.domain.ports.NotificationRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class NoOpAlertNotificationAdapterTest {

    @Mock
    private NotificationRepositoryPort notificationRepositoryPort;

    @Test
    void shouldPersistNotificationWhenAlertIsSent() {
        NoOpAlertNotificationAdapter adapter = new NoOpAlertNotificationAdapter(notificationRepositoryPort);

        Alert alert = new Alert(
                "alert-1",
                "gh-1",
                "reading-1",
                "rule-1",
                "temperature",
                "Threshold exceeded for temperature: 32.5",
                "critical",
                new BigDecimal("32.5"),
                "pending",
                Instant.parse("2026-05-18T10:00:00Z")
        );

        adapter.send(alert);

        ArgumentCaptor<com.proyectosu.invernadero.notifications.center.domain.model.Notification> captor =
                ArgumentCaptor.forClass(com.proyectosu.invernadero.notifications.center.domain.model.Notification.class);

        verify(notificationRepositoryPort).save(captor.capture());

        assertEquals("alert-1", captor.getValue().getAlertId());
        assertEquals("unread", captor.getValue().getStatus());
        assertEquals("critical", captor.getValue().getSeverity());
    }
}