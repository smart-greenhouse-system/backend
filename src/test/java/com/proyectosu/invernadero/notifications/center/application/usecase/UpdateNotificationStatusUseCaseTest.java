package com.proyectosu.invernadero.notifications.center.application.usecase;

import com.proyectosu.invernadero.notifications.center.domain.model.Notification;
import com.proyectosu.invernadero.notifications.center.domain.ports.NotificationRepositoryPort;
import com.proyectosu.invernadero.shared.errores.ApiException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateNotificationStatusUseCaseTest {

    @Mock
    private NotificationRepositoryPort notificationRepositoryPort;

    private UpdateNotificationStatusUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new UpdateNotificationStatusUseCase(notificationRepositoryPort);
    }

    @Test
    void shouldUpdateStatusToRead() {
        Notification current = buildNotification("n-1", "unread");
        when(notificationRepositoryPort.findById("n-1")).thenReturn(Optional.of(current));
        when(notificationRepositoryPort.save(any(Notification.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Notification updated = useCase.ejecutar("n-1", "read");

        assertEquals("read", updated.getStatus());
    }

    @Test
    void shouldRejectInvalidStatus() {
        assertThrows(ApiException.class, () -> useCase.ejecutar("n-1", "invalid"));
    }

    private Notification buildNotification(String id, String status) {
        return new Notification(
                id,
                "alert-1",
                "Threshold alert",
                "Threshold exceeded for temperature",
                "warning",
                status,
                "Monitor the greenhouse",
                Instant.parse("2026-05-18T10:00:00Z")
        );
    }
}