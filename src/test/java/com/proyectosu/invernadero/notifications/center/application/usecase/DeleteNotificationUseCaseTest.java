package com.proyectosu.invernadero.notifications.center.application.usecase;

import com.proyectosu.invernadero.notifications.center.domain.model.Notification;
import com.proyectosu.invernadero.notifications.center.domain.ports.NotificationRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeleteNotificationUseCaseTest {

    @Mock
    private NotificationRepositoryPort notificationRepositoryPort;

    private DeleteNotificationUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new DeleteNotificationUseCase(notificationRepositoryPort);
    }

    @Test
    void shouldDeleteExistingNotification() {
        when(notificationRepositoryPort.findById("n-1")).thenReturn(Optional.of(buildNotification()));

        useCase.ejecutar("n-1");

        verify(notificationRepositoryPort).deleteById("n-1");
    }

    private Notification buildNotification() {
        return new Notification(
                "n-1",
                "alert-1",
                "Threshold alert",
                "Threshold exceeded for temperature",
                "warning",
                "unread",
                "Monitor the greenhouse",
                Instant.parse("2026-05-18T10:00:00Z")
        );
    }
}