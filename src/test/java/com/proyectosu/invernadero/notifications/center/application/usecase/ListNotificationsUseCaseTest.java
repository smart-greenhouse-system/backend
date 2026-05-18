package com.proyectosu.invernadero.notifications.center.application.usecase;

import com.proyectosu.invernadero.notifications.center.domain.model.Notification;
import com.proyectosu.invernadero.notifications.center.domain.ports.NotificationRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListNotificationsUseCaseTest {

    @Mock
    private NotificationRepositoryPort notificationRepositoryPort;

    private ListNotificationsUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new ListNotificationsUseCase(notificationRepositoryPort);
    }

    @Test
    void shouldSortDescendingAndExcludeArchived() {
        when(notificationRepositoryPort.findAll()).thenReturn(List.of(
                buildNotification("n-1", "unread", "2026-05-17T10:00:00Z"),
                buildNotification("n-2", "read", "2026-05-18T10:00:00Z"),
                buildNotification("n-3", "archived", "2026-05-19T10:00:00Z")
        ));

        List<Notification> notifications = useCase.ejecutar();

        assertEquals(2, notifications.size());
        assertEquals("n-2", notifications.get(0).getId());
        assertEquals("n-1", notifications.get(1).getId());
    }

    private Notification buildNotification(String id, String status, String timestamp) {
        return new Notification(
                id,
                "alert-1",
                "Threshold alert",
                "Threshold exceeded for temperature",
                "warning",
                status,
                "Monitor the greenhouse",
                Instant.parse(timestamp)
        );
    }
}