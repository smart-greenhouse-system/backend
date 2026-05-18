package com.proyectosu.invernadero.notifications.center.inbound.controller;

import com.proyectosu.invernadero.notifications.center.application.usecase.DeleteNotificationUseCase;
import com.proyectosu.invernadero.notifications.center.application.usecase.ListNotificationsUseCase;
import com.proyectosu.invernadero.notifications.center.application.usecase.UpdateNotificationStatusUseCase;
import com.proyectosu.invernadero.notifications.center.domain.model.Notification;
import com.proyectosu.invernadero.notifications.center.dto.request.UpdateNotificationStatusRequest;
import com.proyectosu.invernadero.shared.errores.ApiException;
import com.proyectosu.invernadero.shared.errores.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.time.Instant;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class NotificationsControllerTest {

    @Mock
    private ListNotificationsUseCase listNotificationsUseCase;

    @Mock
    private UpdateNotificationStatusUseCase updateNotificationStatusUseCase;

    @Mock
    private DeleteNotificationUseCase deleteNotificationUseCase;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.afterPropertiesSet();

        mockMvc = MockMvcBuilders.standaloneSetup(
                        new NotificationsController(
                                listNotificationsUseCase,
                                updateNotificationStatusUseCase,
                                deleteNotificationUseCase
                        )
                )
                .setControllerAdvice(new GlobalExceptionHandler())
                .setValidator(validator)
                .build();
    }

    @Test
    void shouldListNotifications() throws Exception {
        when(listNotificationsUseCase.ejecutar()).thenReturn(List.of(sampleNotification("n-1")));

        mockMvc.perform(get("/api/v1/notifications"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.notifications[0].notification_id").value("n-1"));
    }

    @Test
    void shouldUpdateNotificationStatus() throws Exception {
        when(updateNotificationStatusUseCase.ejecutar(eq("n-1"), eq("read"))).thenReturn(sampleNotification("n-1").withStatus("read"));

        mockMvc.perform(patch("/api/v1/notifications/{notification_id}", "n-1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                { "status": "read" }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("read"));
    }

    @Test
    void shouldDeleteNotification() throws Exception {
        mockMvc.perform(delete("/api/v1/notifications/{notification_id}", "n-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Notification deleted successfully"));
    }

    @Test
    void shouldReturnNotificationNotFoundWhenUseCaseFails() throws Exception {
        when(updateNotificationStatusUseCase.ejecutar(eq("n-1"), eq("read")))
                .thenThrow(new ApiException(HttpStatus.NOT_FOUND, "NOTIFICATION_NOT_FOUND", "Notification not found"));

        mockMvc.perform(patch("/api/v1/notifications/{notification_id}", "n-1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                { "status": "read" }
                                """))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("NOTIFICATION_NOT_FOUND"));
    }

    private Notification sampleNotification(String id) {
        return new Notification(
                id,
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